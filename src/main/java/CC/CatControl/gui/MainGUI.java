package CC.CatControl.gui;

import CC.CatControl.service.Cat;

import CC.CatControl.service.CatServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@CssImport("./themes/shared-styles.css")
public class MainGUI extends VerticalLayout {
    private Grid<Cat> grid = new Grid<>(Cat.class);
    private TextField textField = new TextField();
    private CatServiceImpl catServiceImpl;
    private CatEditGUI catEditGUI;

    @Autowired
    public MainGUI (CatServiceImpl catServiceImpl) {
        addClassName ("main-view");
        this.catServiceImpl = catServiceImpl;
        catServiceImpl.readCats();
        setSizeFull();
        add(getTitel(), getToolbar(), getDiv(catServiceImpl));
        updateGrid();
    }

    private Div getDiv(CatServiceImpl catServiceImpl) {
        this.catServiceImpl = catServiceImpl;
        configureGrid();

        catEditGUI = new CatEditGUI(catServiceImpl.getCatlist());
        catEditGUI.addListener(CatEditGUI.SaveEvent.class, this::saveCat);
        catEditGUI.addListener(CatEditGUI.DeleteEvent.class, this::deleteCat);
        catEditGUI.addListener(CatEditGUI.CloseEvent.class, e -> closeEditor());

        Div frontSeite = new Div (grid, catEditGUI);
        frontSeite.addClassName("frontseite");
        frontSeite.setSizeFull();
        return frontSeite;
    }

    private HorizontalLayout getTitel() {
        HorizontalLayout kopfZeile = new HorizontalLayout();
        kopfZeile.setWidth("100%");

        VerticalLayout kopfSpalte = new VerticalLayout();
        Label title = new Label("CatControl");
        Label title2 = new Label("Gibt dir die Kontrolle zurück.");
        kopfSpalte.add(title);
        kopfSpalte.add(title2);
        kopfSpalte.setSizeFull();
        kopfSpalte.setHorizontalComponentAlignment(Alignment.CENTER, title, title2);

        kopfZeile.add(kopfSpalte);
        return kopfZeile;
    }

    private void deleteCat(CatEditGUI.DeleteEvent evt) {
        catServiceImpl.deleteCat(evt.getCat());
        updateGrid();
        closeEditor();
    }

    private void saveCat(CatEditGUI.SaveEvent evt) {
        catServiceImpl.saveCat(evt.getCat());
        updateGrid();
        closeEditor();
    }

    private HorizontalLayout getToolbar() {
        textField.setPlaceholder("Filter by name...");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.addValueChangeListener(e -> updateGrid());

        Button addCatButton = new Button("Add cat", click -> addCat());

        HorizontalLayout toolbar = new HorizontalLayout(textField, addCatButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addCat() {
        grid.asSingleSelect().clear();
        editCat(Cat.getEmptyCat());
    }
    private void configureGrid() {
        grid.addClassName("cat-grid");
        grid.setSizeFull();
        grid.setColumns("name", "alter", "impfdatum", "gewicht", "rund", "suess");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(evt -> editCat(evt.getValue()));
    }

    private void editCat(Cat cat) {
        if (cat == null) {
            closeEditor();
        } else {
            addClassName("editing");
            catEditGUI.setCat(cat);
            catEditGUI.setVisible(true);
        }
    }
    private void closeEditor() {
        catEditGUI.setCat(null);
        catEditGUI.setVisible(false);
        removeClassName("editing");
    }

    private void updateGrid() {
        grid.setItems(catServiceImpl.findAll(textField.getValue()));
    }
}