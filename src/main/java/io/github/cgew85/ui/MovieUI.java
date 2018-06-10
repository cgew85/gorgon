package io.github.cgew85.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.data.HasValue;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import io.github.cgew85.domain.Movie;
import io.github.cgew85.mapper.MovieMapper;
import io.github.cgew85.service.MovieService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.vaadin.shared.data.sort.SortDirection.ASCENDING;
import static com.vaadin.ui.Alignment.BOTTOM_RIGHT;
import static com.vaadin.ui.Notification.Type.WARNING_MESSAGE;
import static java.util.Objects.isNull;

@SpringUI
@Theme("valo")
public class MovieUI extends UI {

    private final MovieService movieService;
    private final MovieMapper movieMapper;

    private TextField textFieldName;
    private ComboBox<String> comboBoxCut;
    private ComboBox<String> comboBoxCasing;
    private ComboBox<String> comboBoxFormat;
    private Button buttonAddMovie;
    private Button buttonRemoveMovie;
    private Grid grid;
    private Label labelAppName;
    private Label labelSeparator;
    private final Consumer<HasValue> clearInputField = HasValue::clear;
    private static final int FULL_WIDTH = 100;

    @Autowired
    public MovieUI(MovieService movieService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        val verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        verticalLayout.addComponents(getLabelAppName(), getAddMovieLine(), getLabelSeparator());
        verticalLayout.addComponentsAndExpand(getGrid());
        verticalLayout.addComponent(getButtonRemoveMovie());

        setContent(verticalLayout);
    }

    private HorizontalLayout getAddMovieLine() {
        val horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponentsAndExpand(getTextFieldName(), getComboBoxCut(), getComboBoxCasing(), getComboBoxFormat(), getButtonAddMovie());
        horizontalLayout.setComponentAlignment(buttonAddMovie, BOTTOM_RIGHT);

        return horizontalLayout;
    }

    @SuppressWarnings("unchecked")
    private void getClickListenerAdd(Button.ClickEvent clickEvent) {
        if (!textFieldName.isEmpty() && !comboBoxCut.isEmpty() && !comboBoxCasing.isEmpty() && !comboBoxFormat.isEmpty()) {
            val movie = new Movie();
            movie.setName(textFieldName.getValue().trim());
            movie.setCasing((Movie.CASING) movieMapper.mapCutCasingOrFormat(comboBoxCasing.getValue(), Movie.CASING.class));
            movie.setCut((Movie.CUT) movieMapper.mapCutCasingOrFormat(comboBoxCut.getValue(), Movie.CUT.class));
            movie.setFormat((Movie.FORMAT) movieMapper.mapCutCasingOrFormat(comboBoxFormat.getValue(), Movie.FORMAT.class));
            movieService.saveMovie(movie);

            Stream.of(textFieldName, comboBoxCasing, comboBoxCut, comboBoxFormat).forEach(clearInputField);
            grid.setItems(getAllMovies());
            grid.sort("name", ASCENDING);
        } else {
            Notification.show("Missing input", WARNING_MESSAGE);
        }
    }

    private void getSelectionListener(SelectionEvent selectionEvent) {
        if (!selectionEvent.getAllSelectedItems().isEmpty()) {
            buttonRemoveMovie.setEnabled(true);
        } else {
            buttonRemoveMovie.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    private void getClickListenerRemove(Button.ClickEvent clickEvent) {
        if (!grid.getSelectedItems().isEmpty()) {
            grid.getSelectedItems().stream().findFirst().ifPresent(movie -> {
                movieService.deleteMovie((Movie) movie);
                grid.setItems(getAllMovies());
            });
        }
    }

    private TextField getTextFieldName() {
        if (isNull(textFieldName)) {
            textFieldName = new TextField("Name: ");
        }

        return textFieldName;
    }

    private ComboBox<String> getComboBoxCut() {
        if (isNull(comboBoxCut)) {
            comboBoxCut = new ComboBox<>("Cut");
            comboBoxCut.setItems(movieMapper.getMapCutUiTexts().stream().sorted());
        }

        return comboBoxCut;
    }

    private ComboBox<String> getComboBoxCasing() {
        if (isNull(comboBoxCasing)) {
            comboBoxCasing = new ComboBox<>("Casing");
            comboBoxCasing.setItems(movieMapper.getMapCasingUiTexts().stream().sorted());
        }

        return comboBoxCasing;
    }

    private ComboBox<String> getComboBoxFormat() {
        if (isNull(comboBoxFormat)) {
            comboBoxFormat = new ComboBox<>("Format");
            comboBoxFormat.setItems(movieMapper.getMapFormatUiTexts().stream().sorted());
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
            grid.setItems(getAllMovies());
            grid.setWidth(FULL_WIDTH, Unit.PERCENTAGE);
            grid.removeColumn("objectId");
            grid.removeColumn("cut");
            grid.removeColumn("casing");
            grid.removeColumn("format");
            grid.getColumn("casingUi").setCaption("Casing");
            grid.getColumn("cutUi").setCaption("Cut");
            grid.getColumn("formatUi").setCaption("Format");
            grid.setColumnOrder("name", "cutUi", "casingUi", "formatUi");
            grid.addSelectionListener(this::getSelectionListener);
            grid.sort("name", ASCENDING);
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

    private List<Movie> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        movies.forEach(movie -> {
            movie.setCasingUi(movieMapper.getUiTextCasing(movie.getCasing()));
            movie.setCutUi(movieMapper.getUiTextCut(movie.getCut()));
            movie.setFormatUi(movieMapper.getUiTextFormat(movie.getFormat()));
        });

        return movies;
    }
}
