package io.github.cgew85;

import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

@SpringUI
@Theme("valo")
public class MovieUI extends UI {

    private final MovieRepository movieRepository;

    private TextField textFieldName;
    private ComboBox<Movie.CUT> comboBoxCut;
    private ComboBox<Movie.CASING> comboBoxCasing;
    private Grid<Movie> grid;

    @Autowired
    public MovieUI(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        final Label label = new Label("Gorgon");
        label.addStyleName(ValoTheme.LABEL_BOLD);
        label.addStyleName(ValoTheme.LABEL_HUGE);
        label.setSizeUndefined();

        grid = new Grid<>(Movie.class);
        grid.setItems(movieRepository.findAll());
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.removeColumn("objectId");
        grid.setColumnOrder("name", "cut", "casing");

        verticalLayout.addComponents(label);
        verticalLayout.addComponent(getAddMovieLine());
        final Label labelSeparator = new Label("<hr />");
        labelSeparator.setContentMode(ContentMode.HTML);
        labelSeparator.setWidth(100, Unit.PERCENTAGE);
        verticalLayout.addComponents(labelSeparator);
        verticalLayout.addComponentsAndExpand(grid);

        setContent(verticalLayout);
    }

    private HorizontalLayout getAddMovieLine() {
        final HorizontalLayout horizontalLayout = new HorizontalLayout();
        textFieldName = new TextField("Name: ");

        comboBoxCut = new ComboBox<>("Cut");
        comboBoxCut.setItems(Movie.CUT.DIRECTORS_CUT, Movie.CUT.THEATRICAL_CUT);

        comboBoxCasing = new ComboBox<>("Casing");
        comboBoxCasing.setItems(Movie.CASING.AMARAY, Movie.CASING.STEELBOOK);

        final Button buttonAddMovie = new Button();
        buttonAddMovie.setIcon(FontAwesome.PLUS);
        buttonAddMovie.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        buttonAddMovie.addClickListener(this::getClickListener);

        horizontalLayout.addComponentsAndExpand(textFieldName);
        horizontalLayout.addComponentsAndExpand(comboBoxCut);
        horizontalLayout.addComponentsAndExpand(comboBoxCasing);
        horizontalLayout.addComponentsAndExpand(buttonAddMovie);
        horizontalLayout.setComponentAlignment(buttonAddMovie, Alignment.BOTTOM_RIGHT);

        return horizontalLayout;
    }

    private void getClickListener(Button.ClickEvent clickEvent) {
        if (!textFieldName.isEmpty() && !comboBoxCut.isEmpty() && !comboBoxCasing.isEmpty()) {
            Movie movie = new Movie();
            movie.setName(textFieldName.getValue().trim());
            movie.setCasing(comboBoxCasing.getValue());
            movie.setCut(comboBoxCut.getValue());
            movieRepository.save(movie);

            textFieldName.clear();
            comboBoxCasing.clear();
            comboBoxCut.clear();
            grid.setItems(Collections.EMPTY_LIST);
            grid.setItems(movieRepository.findAll());
        } else {
            Notification.show("Missing input", Notification.Type.WARNING_MESSAGE);
        }
    }
}
