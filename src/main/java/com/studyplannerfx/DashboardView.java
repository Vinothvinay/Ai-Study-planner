package com.studyplannerfx;

import com.model.Subject;
import com.model.Task;
import com.services.AIService;
import com.util.UI;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.lang.classfile.Label;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.swing.plaf.synth.Region;
import javax.swing.text.html.ListView;

/**
 * Main dashboard view (View + Controller for simplicity).
 * - Subjects panel (add/delete)
 * - Daily hours spinner
 * - API key field (for Hugging Face)
 * - Calendar grid (visual)
 * - Progress pie chart
 * - Timetable tabs (Mon-Sun) with drag-and-drop across days
 * - Generate (AI) button with loading overlay
 * - Theme toggle
 */
public class DashboardView {

    private final BorderPane root = new BorderPane();

    // Left Sidebar (Subjects + Settings)
    private final ObservableList<Subject> subjects = FXCollections.observableArrayList();
    private final ListView<Subject> subjectsList = new ListView<>(subjects);
    private final Spinner<Double> hoursSpinner = new Spinner<>(1.0, 16.0, 3.0, 0.5);
    // API key is hardcoded for interview demo
   String HF_TOKEN = System.getenv("HF_TOKEN");


    // Top bar
    private final ToggleButton themeToggle = new ToggleButton("Dark Mode");
    private Consumer<Boolean> themeSwitcher;

    // Center
    private final GridPane calendar = new GridPane();
    private final PieChart progressChart = new PieChart();

    // Timetable tabs
    private final TabPane timetableTabs = new TabPane();
    private final Map<DayOfWeek, ListView<Task>> taskLists = new EnumMap<>(DayOfWeek.class);
    private final Map<DayOfWeek, ObservableList<Task>> tasksByDay = new EnumMap<>(DayOfWeek.class);

    // Overlay
    private final StackPane overlay = new StackPane();

    // Quotes (static for frontend simplicity)
    private final List<String> quotes = List.of(
        "“Success is the sum of small efforts, repeated day in and day out.” — Robert Collier",
        "“It always seems impossible until it’s done.” — Nelson Mandela",
        "“The secret of getting ahead is getting started.” — Mark Twain",
        "“Don’t watch the clock; do what it does. Keep going.” — Sam Levenson",
        "“Discipline is choosing what you want most over what you want now.”"
    );

    public DashboardView() {
        buildUI();
        refreshCalendar(YearMonth.now());
        refreshProgress();
        applyDndAcrossAllDays();
    }

    public Node getRoot() {
        return overlayWrap(root);
    }

    public void setThemeSwitcher(Consumer<Boolean> switcher) {
        this.themeSwitcher = switcher;
    }

    private StackPane overlayWrap(Node content) {
        StackPane wrap = new StackPane(content, overlay);
        overlay.setVisible(false);
        overlay.setPickOnBounds(true);
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setPrefSize(64, 64);
        Label lbl = new Label("Thinking...");
        lbl.getStyleClass().add("muted");
        box.getChildren().addAll(spinner, lbl);
        overlay.getStyleClass().add("overlay");
        overlay.getChildren().add(box);
        return wrap;
    }

    private void setLoading(boolean on) {
        overlay.setVisible(on);
        UI.fade(overlay, on ? 1.0 : 0.0, 200);
    }

    private void buildUI() {
        // Top bar
        HBox top = new HBox(10);
        top.getStyleClass().add("topbar");
        top.setPadding(new Insets(12, 16, 12, 16));
        Label title = new Label("AI Study Planner");
        title.getStyleClass().add("app-title");

        Button generateBtn = new Button("Generate Timetable (AI)");
        generateBtn.getStyleClass().add("primary");
        generateBtn.setOnAction(e -> onGenerateAI());

        Button exportBtn = new Button("Export (Preview-only)");
        exportBtn.setOnAction(e -> UI.info("Export", "This frontend-only build does not export files."));

        themeToggle.setOnAction(e -> {
            boolean dark = themeToggle.isSelected();
            themeToggle.setText(dark ? "Light Mode" : "Dark Mode");
            if (themeSwitcher != null) themeSwitcher.accept(dark);
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        top.getChildren().addAll(title, spacer, generateBtn, exportBtn, themeToggle);
        root.setTop(top);

        // Left: subjects/settings
        VBox left = new VBox(8);
        left.getStyleClass().add("sidebar");
        left.setPrefWidth(300);
        left.setPadding(new Insets(12, 8, 12, 12));
        Label subjTitle = new Label("Subjects");
        subjTitle.getStyleClass().add("section-title");

        subjectsList.setPlaceholder(new Label("No subjects yet"));
        HBox subjButtons = new HBox(8);
        Button addSubject = new Button("Add Subject");
        Button deleteSubject = new Button("Delete");
        subjButtons.getChildren().addAll(addSubject, deleteSubject);

        addSubject.setOnAction(e -> addSubjectDialog());
        deleteSubject.setOnAction(e -> {
            Subject s = subjectsList.getSelectionModel().getSelectedItem();
            if (s != null) subjects.remove(s);
        });

        Separator sep1 = new Separator();
        Label hoursLbl = new Label("Daily Hours");
        hoursLbl.getStyleClass().add("section-title");
        hoursSpinner.setEditable(true);
        hoursSpinner.setMaxWidth(160);

        Separator sep2 = new Separator();
        // API key is now hardcoded - no input field needed
        Label apiLbl = new Label("AI Integration Ready");
        apiLbl.getStyleClass().add("section-title");
        Label apiStatus = new Label("✓ API Key Configured");
        apiStatus.getStyleClass().add("muted");

        Separator sep3 = new Separator();
        Label quoteTitle = new Label("Quote of the Day");
        quoteTitle.getStyleClass().add("section-title");
        Text quote = new Text(quotes.get(new Random().nextInt(quotes.size())));
        quote.setWrappingWidth(260);

        left.getChildren().addAll(
            subjTitle, subjectsList, subjButtons,
            sep1, hoursLbl, hoursSpinner,
            sep2, apiLbl, apiStatus,
            sep3, quoteTitle, quote
        );
        root.setLeft(left);

        // Center: calendar + progress + timetable
        VBox center = new VBox(12);

        HBox topRow = new HBox(12);
        VBox calendarBox = new VBox(8);
        Label calTitle = new Label("Calendar");
        calTitle.getStyleClass().add("section-title");
        calendar.getStyleClass().add("calendar");
        calendarBox.getChildren().addAll(calTitle, calendar);
        VBox.setVgrow(calendar, Priority.ALWAYS);

        VBox progressBox = new VBox(8);
        Label progTitle = new Label("Progress");
        progTitle.getStyleClass().add("section-title");
        progressChart.setPrefSize(380, 260);
        progressBox.getChildren().addAll(progTitle, progressChart);
        HBox.setHgrow(calendarBox, Priority.ALWAYS);
        HBox.setHgrow(progressBox, Priority.NEVER);

        topRow.getChildren().addAll(calendarBox, progressBox);

        VBox timetableBox = new VBox(8);
        Label tTitle = new Label("Timetable (Drag between days; check to mark done)");
        tTitle.getStyleClass().add("section-title");
        buildTimetableTabs();
        VBox.setVgrow(timetableTabs, Priority.ALWAYS);
        timetableBox.getChildren().addAll(tTitle, timetableTabs);

        center.getChildren().addAll(topRow, new Separator(), timetableBox);
        VBox.setVgrow(timetableBox, Priority.ALWAYS);
        root.setCenter(center);
    }

    private void buildTimetableTabs() {
        timetableTabs.getTabs().clear();
        tasksByDay.clear();
        taskLists.clear();
        for (DayOfWeek d : DayOfWeek.values()) {
            ObservableList<Task> list = FXCollections.observableArrayList();
            tasksByDay.put(d, list);
            ListView<Task> lv = new ListView<>(list);
            lv.setCellFactory(v -> taskCell());
            taskLists.put(d, lv);
            Tab tab = new Tab(d.name(), lv);
            tab.setClosable(false);
            timetableTabs.getTabs().add(tab);
        }
    }

    private ListCell<Task> taskCell() {
        return new ListCell<>() {
            private final CheckBox check = new CheckBox();
            private final Label label = new Label();
            private final HBox box = new HBox(8, check, label);
            {
                box.setAlignment(Pos.CENTER_LEFT);
            }
            @Override protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    check.setSelected(item.isDone());
                    label.textProperty().bind(Bindings.createStringBinding(
                        () -> String.format("[%s] %s • %.1fh", item.getSubject(), item.getTitle(), item.getHours()),
                        item.doneProperty(), item.titleProperty(), item.hoursProperty()));
                    check.setOnAction(e -> item.setDone(check.isSelected()));
                    setGraphic(box);
                }
            }
        };
    }

    private void applyDndAcrossAllDays() {
        for (DayOfWeek d : DayOfWeek.values()) {
            ListView<Task> lv = taskLists.get(d);
            // Drag source
            lv.setOnDragDetected(ev -> {
                Task sel = lv.getSelectionModel().getSelectedItem();
                if (sel == null) return;
                Dragboard db = lv.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent cc = new ClipboardContent();
                cc.putString(sel.getId()); // use id string
                db.setContent(cc);
                ev.consume();
            });
            // Drag over
            lv.setOnDragOver(ev -> {
                if (ev.getGestureSource() != lv && ev.getDragboard().hasString()) {
                    ev.acceptTransferModes(TransferMode.MOVE);
                }
                ev.consume();
            });
            // Drop
            lv.setOnDragDropped(ev -> {
                Dragboard db = ev.getDragboard();
                boolean ok = false;
                if (db.hasString()) {
                    String id = db.getString();
                    Task dragged = findTaskById(id);
                    if (dragged != null) {
                        // remove from its list
                        tasksByDay.values().forEach(list -> list.remove(dragged));
                        // add to target
                        tasksByDay.get(d).add(dragged.withDate(nextDateOf(d)));
                        ok = true;
                        refreshProgress();
                    }
                }
                ev.setDropCompleted(ok);
                ev.consume();
            });
        }
    }

    private Task findTaskById(String id) {
        for (ObservableList<Task> list : tasksByDay.values()) {
            for (Task t : list) if (Objects.equals(t.getId(), id)) return t;
        }
        return null;
    }

    private void addSubjectDialog() {
        Dialog<Subject> dialog = new Dialog<>();
        dialog.setTitle("Add Subject");
        ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, save);

        TextField name = new TextField();
        name.setPromptText("Subject name");
        Spinner<Integer> diff = new Spinner<>(1, 5, 3);
        diff.setEditable(true);
        Spinner<Double> hours = new Spinner<>(1.0, 200.0, 20.0, 0.5);
        hours.setEditable(true);
        DatePicker exam = new DatePicker();

        GridPane gp = new GridPane();
        gp.setHgap(8); gp.setVgap(8); gp.setPadding(new Insets(12));
        gp.addRow(0, new Label("Name"), name);
        gp.addRow(1, new Label("Difficulty"), diff);
        gp.addRow(2, new Label("Target Hours"), hours);
        gp.addRow(3, new Label("Exam Date"), exam);
        dialog.getDialogPane().setContent(gp);

        dialog.setResultConverter(bt -> {
            if (bt == save && !name.getText().isBlank()) {
                return new Subject(name.getText().trim(), diff.getValue(), hours.getValue(), exam.getValue());
            }
            return null;
        });

        Optional<Subject> res = dialog.showAndWait();
        res.ifPresent(subjects::add);
    }

    private void refreshCalendar(YearMonth ym) {
        calendar.getChildren().clear();
        calendar.getColumnConstraints().clear();
        calendar.getRowConstraints().clear();
        for (int i = 0; i < 7; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0/7);
            calendar.getColumnConstraints().add(cc);
        }
        for (int i = 0; i < 7; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100.0/7);
            calendar.getRowConstraints().add(rc);
        }

        String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        for (int i=0;i<7;i++) {
            Label l = new Label(days[i]);
            l.getStyleClass().add("muted");
            GridPane.setHalignment(l, HPos.CENTER);
            calendar.add(l, i, 0);
        }

        LocalDate first = ym.atDay(1);
        int shift = (first.getDayOfWeek().getValue()+6)%7; // Monday=0
        int length = ym.lengthOfMonth();

        int row = 1;
        int col = shift;
        for (int day=1; day<=length; day++) {
            LocalDate date = ym.atDay(day);
            VBox cell = new VBox(4);
            cell.setPadding(new Insets(6));
            cell.getStyleClass().add("calendar-cell");
            Label dl = new Label(String.valueOf(day));
            cell.getChildren().add(dl);
            calendar.add(cell, col, row);
            col++;
            if (col>6) { col=0; row++; }
        }
    }

    private void refreshProgress() {
        List<Task> all = tasksByDay.values().stream()
            .flatMap(Collection::stream).toList();
        long total = all.size();
        long done = all.stream().filter(Task::isDone).count();
        double pct = total == 0 ? 0.0 : (done * 100.0 / total);

        PieChart.Data d1 = new PieChart.Data("Done", pct);
        PieChart.Data d2 = new PieChart.Data("Remaining", Math.max(0, 100 - pct));
        progressChart.setData(FXCollections.observableArrayList(d1, d2));
    }

    private LocalDate nextDateOf(DayOfWeek d) {
        LocalDate today = LocalDate.now();
        int diff = d.getValue() - today.getDayOfWeek().getValue();
        if (diff < 0) diff += 7;
        return today.plusDays(diff);
    }

    private void onGenerateAI() {
        // API key is now hardcoded - no validation needed
        double dailyHours = hoursSpinner.getValue();
        List<Subject> subs = new ArrayList<>(subjects);

        if (subs.isEmpty()) {
            UI.warn("No Subjects", "Please add at least one subject first.");
            return;
        }

        setLoading(true);
        new Thread(() -> {
            try {
                String plan = AIService.generatePlanFromHF(API_KEY, subs, dailyHours);
                List<Task> tasks = AIService.parseTasksFromText(plan);
                // Clear current and apply new tasks grouped by weekday
                Platform.runLater(() -> {
                    tasksByDay.values().forEach(ObservableList::clear);
                    Map<DayOfWeek, List<Task>> map = tasks.stream().collect(Collectors.groupingBy(t ->
                        t.getDate().getDayOfWeek()));
                    for (DayOfWeek d : DayOfWeek.values()) {
                        tasksByDay.get(d).addAll(map.getOrDefault(d, List.of()));
                    }
                    refreshProgress();
                    UI.fade(root, 1.0, 250);
                });
            } catch (Exception ex) {
                Platform.runLater(() -> UI.error("AI Error", ex.getMessage()));
            } finally {
                Platform.runLater(() -> setLoading(false));
            }
        }).start();
    }
}