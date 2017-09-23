package io.github.cgew85;

import com.vaadin.annotations.Theme;
import com.vaadin.data.HasValue;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@SpringUI
@Theme("valo")
public class MovieUI extends UI {

    private final MovieRepository movieRepository;

    private TextField textFieldName;
    private ComboBox<Movie.CUT> comboBoxCut;
    private ComboBox<Movie.CASING> comboBoxCasing;
    private ComboBox<Movie.FORMAT> comboBoxFormat;
    private Button buttonAddMovie;
    private Button buttonRemoveMovie;
    private Grid grid;
    private Label labelAppName;
    private Label labelSeparator;
    private final Consumer<HasValue> clearInputField = HasValue::clear;
    private static final int FULL_WIDTH = 100;

    @Autowired
    public MovieUI(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        verticalLayout.addComponents(getLabelAppName(), getAddMovieLine(), getLabelSeparator());
        verticalLayout.addComponentsAndExpand(getGrid());
        verticalLayout.addComponent(getButtonRemoveMovie());

        setContent(verticalLayout);
    }

    private HorizontalLayout getAddMovieLine() {
        final HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponentsAndExpand(getTextFieldName(), getComboBoxCut(), getComboBoxCasing(), getComboBoxFormat(), getButtonAddMovie());
        horizontalLayout.setComponentAlignment(buttonAddMovie, Alignment.BOTTOM_RIGHT);

        return horizontalLayout;
    }

    @SuppressWarnings("unchecked")
    private void getClickListenerAdd(Button.ClickEvent clickEvent) {
        if (!textFieldName.isEmpty() && !comboBoxCut.isEmpty() && !comboBoxCasing.isEmpty() && !comboBoxFormat.isEmpty()) {
            Movie movie = new Movie();
            movie.setName(textFieldName.getValue().trim());
            movie.setCasing(comboBoxCasing.getValue());
            movie.setCut(comboBoxCut.getValue());
            movie.setFormat(comboBoxFormat.getValue());
            movieRepository.save(movie);

            Stream.of(textFieldName, comboBoxCasing, comboBoxCut, comboBoxFormat).forEach(clearInputField);
            grid.setItems(movieRepository.findAll());
            grid.sort("name", SortDirection.ASCENDING);
        } else {
            Notification.show("Missing input", Notification.Type.WARNING_MESSAGE);
        }
    }

    private void getSelectionListener(SelectionEvent selectionEvent) {
        if (selectionEvent.getAllSelectedItems().size() > 0) {
            buttonRemoveMovie.setEnabled(true);
        } else {
            buttonRemoveMovie.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    private void getClickListenerRemove(Button.ClickEvent clickEvent) {
        if (grid.getSelectedItems().size() > 0) {
            grid.getSelectedItems().stream().findFirst().ifPresent(movie -> {
                movieRepository.delete((Movie) movie);
                grid.setItems(movieRepository.findAll());
            });
        }
    }

    private TextField getTextFieldName() {
        if (isNull(textFieldName)) {
            textFieldName = new TextField("Name: ");
        }

        return textFieldName;
    }

    private ComboBox<Movie.CUT> getComboBoxCut() {
        if (isNull(comboBoxCut)) {
            comboBoxCut = new ComboBox<>("Cut");
            comboBoxCut.setItems(Movie.CUT.DIRECTORS_CUT, Movie.CUT.THEATRICAL_CUT);
        }

        return comboBoxCut;
    }

    private ComboBox<Movie.CASING> getComboBoxCasing() {
        if (isNull(comboBoxCasing)) {
            comboBoxCasing = new ComboBox<>("Casing");
            comboBoxCasing.setItems(Movie.CASING.AMARAY, Movie.CASING.STEELBOOK);
        }

        return comboBoxCasing;
    }

    private ComboBox<Movie.FORMAT> getComboBoxFormat() {
        if (isNull(comboBoxFormat)) {
            comboBoxFormat = new ComboBox<>("Format");
            comboBoxFormat.setItems(Movie.FORMAT.BLURAY, Movie.FORMAT.DVD, Movie.FORMAT.VHS);
        }

        return comboBoxFormat;
    }

    private Button getButtonAddMovie() {
        if (isNull(buttonAddMovie)) {
            buttonAddMovie = new Button();
            buttonAddMovie.setIcon(FontAwesome.PLUS);
            buttonAddMovie.addStyleName(ValoTheme.BUTTON_FRIENDLY);
            buttonAddMovie.addClickListener(this::getClickListenerAdd);
        }

        return buttonAddMovie;
    }

    private Button getButtonRemoveMovie() {
        if (isNull(buttonRemoveMovie)) {
            buttonRemoveMovie = new Button();
            buttonRemoveMovie.setIcon(FontAwesome.MINUS);
            buttonRemoveMovie.addStyleName(ValoTheme.BUTTON_QUIET);
            buttonRemoveMovie.setEnabled(false);
            buttonRemoveMovie.addClickListener(this::getClickListenerRemove);
        }

        return buttonRemoveMovie;
    }

    @SuppressWarnings("unchecked")
    private Grid<Movie> getGrid() {
        if (isNull(grid)) {
            grid = new Grid<>(Movie.class);
            grid.setItems(movieRepository.findAll());
            grid.setWidth(FULL_WIDTH, Unit.PERCENTAGE);
            grid.removeColumn("objectId");
            grid.setColumnOrder("name", "cut", "casing", "format");
            grid.addSelectionListener(this::getSelectionListener);
            grid.sort("name", SortDirection.ASCENDING);
        }

        return grid;
    }

    private Label getLabelAppName() {
        if (isNull(labelAppName)) {
            labelAppName = new Label("Gorgon");
            labelAppName.addStyleName(ValoTheme.LABEL_BOLD);
            labelAppName.addStyleName(ValoTheme.LABEL_HUGE);
            labelAppName.setSizeUndefined();
        }

        return labelAppName;
    }

    private Label getLabelSeparator() {
        if (isNull(labelSeparator)) {
            labelSeparator = new Label("<hr />");
            labelSeparator.setContentMode(ContentMode.HTML);
            labelSeparator.setWidth(FULL_WIDTH, Unit.PERCENTAGE);
        }

        return labelSeparator;
    }
}
