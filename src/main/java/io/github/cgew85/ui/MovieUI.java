package io.github.cgew85.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;
import io.github.cgew85.domain.Movie;
import io.github.cgew85.mapper.MovieMapper;
import io.github.cgew85.service.MovieService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Route("")
@Theme(value = Lumo.class, variant = Material.LIGHT)
public class MovieUI extends VerticalLayout {

    private final MovieService movieService;
    private final MovieMapper movieMapper;

    private TextField textFieldName;
    private TextField textFieldReleaseYear;
    private ComboBox<String> comboBoxCut;
    private ComboBox<String> comboBoxCasing;
    private ComboBox<String> comboBoxFormat;
    private Button buttonAddMovie;
    private Button buttonRemoveMovie;
    private Grid grid;
    private Label labelAppName;
    private Label labelSeparator;
    private final Consumer<HasValue> clearInputField = HasValue::clear;

    @Autowired
    public MovieUI(MovieService movieService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
        init();
    }

    protected void init() {
        this.setSizeFull();
        this.add(getLabelAppName(), getAddMovieLine(), getLabelSeparator(), getGrid(), getButtonRemoveMovie());
    }

    private HorizontalLayout getAddMovieLine() {
        val horizontalLayout = new HorizontalLayout();

        horizontalLayout.add(getTextFieldName(), getTextFieldReleaseYear(), getComboBoxCut(), getComboBoxCasing(), getComboBoxFormat(), getButtonAddMovie());
        horizontalLayout.setWidthFull();

        return horizontalLayout;
    }

    @SuppressWarnings("unchecked")
    private void getClickListenerAdd(ClickEvent clickEvent) {
        if (!textFieldName.isEmpty() && !comboBoxCut.isEmpty() && !comboBoxCasing.isEmpty() && !comboBoxFormat.isEmpty()) {
            val movie = new Movie();
            movie.setName(textFieldName.getValue().trim());
            movie.setReleaseYear(Integer.parseInt(textFieldReleaseYear.getValue().trim()));
            movie.setCasing((Movie.CASING) movieMapper.mapCutCasingOrFormat(comboBoxCasing.getValue(), Movie.CASING.class));
            movie.setCut((Movie.CUT) movieMapper.mapCutCasingOrFormat(comboBoxCut.getValue(), Movie.CUT.class));
            movie.setFormat((Movie.FORMAT) movieMapper.mapCutCasingOrFormat(comboBoxFormat.getValue(), Movie.FORMAT.class));
            movieService.saveMovie(movie);

            Stream.of(textFieldName, textFieldReleaseYear, comboBoxCasing, comboBoxCut, comboBoxFormat).forEach(clearInputField);
            grid.setItems(getAllMovies());
            grid.sort(Collections.singletonList(new GridSortOrder<String>(grid.getColumnByKey("name"), SortDirection.ASCENDING)));
        } else {
            Notification.show("Missing input");
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
    private void getClickListenerRemove(ClickEvent clickEvent) {
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

    public TextField getTextFieldReleaseYear() {
        if (isNull(textFieldReleaseYear)) {
            textFieldReleaseYear = new TextField("Release year:");
        }
        return textFieldReleaseYear;
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
            buttonAddMovie.setIcon(VaadinIcon.PLUS.create());
            buttonAddMovie.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_LARGE);
            buttonAddMovie.addClickListener(this::getClickListenerAdd);
        }

        return buttonAddMovie;
    }

    private Button getButtonRemoveMovie() {
        if (isNull(buttonRemoveMovie)) {
            buttonRemoveMovie = new Button();
            buttonRemoveMovie.setIcon(VaadinIcon.MINUS.create());
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
            grid.setWidthFull();
            grid.removeColumn(grid.getColumnByKey("objectId"));
            grid.removeColumn(grid.getColumnByKey("cut"));
            grid.removeColumn(grid.getColumnByKey("casing"));
            grid.removeColumn(grid.getColumnByKey("format"));
            grid.getColumnByKey("casingUi").setHeader("Casing");
            grid.getColumnByKey("cutUi").setHeader("Cut");
            grid.getColumnByKey("formatUi").setHeader("Format");
            grid.setColumns("name", "releaseYear", "cutUi", "formatUi", "casingUi");
            grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                    GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
            grid.addSelectionListener(this::getSelectionListener);
            grid.sort(Collections.singletonList(new GridSortOrder<String>(grid.getColumnByKey("name"), SortDirection.ASCENDING)));
        }

        return grid;
    }

    private Label getLabelAppName() {
        if (isNull(labelAppName)) {
            labelAppName = new Label("Gorgon");
            labelAppName.setSizeUndefined();
        }

        return labelAppName;
    }

    private Label getLabelSeparator() {
        if (isNull(labelSeparator)) {
            labelSeparator = new Label("");
            labelSeparator.setWidthFull();
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
