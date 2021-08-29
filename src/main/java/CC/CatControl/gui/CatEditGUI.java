package CC.CatControl.gui;

import CC.CatControl.service.Cat;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class CatEditGUI extends FormLayout {

    private Cat cat;
    private TextField name = new TextField("Name der Katze");
    private Select<Integer> alter = new Select<>();
    private DatePicker impfdatum = new DatePicker("Impfdatum");
    private Select<Double> gewicht = new Select<>();
    private RadioButtonGroup<Boolean> rund = new RadioButtonGroup<>();
    private RadioButtonGroup<Boolean> suess = new RadioButtonGroup<>();
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Cancel");
    private Binder<Cat> binder = new BeanValidationBinder<>(Cat.class);


    public CatEditGUI(ArrayList<Cat> cats) {
        addClassName("catedit-gui");
        configureForm();

        binder.bindInstanceFields(this);

        add(name,
                alter,
                impfdatum,
                gewicht,
                createBooleanButtons(),
                createButtonsLayout());
    }

    private void configureForm() {
        LocalDate now = LocalDate.now();
        System.out.println("" + now);
        impfdatum.setValue(now);

        alter.setLabel("Alter der Katze");
        alter.setItems(IntStream.rangeClosed(1, 25).boxed().collect(Collectors.toList()));

        gewicht.setLabel("Gewicht der Katze");
        ArrayList<Double> weightList = new ArrayList<>();
        double doubleGenerator1 = 0.00;
        double doubleGenerator2 = 0;
        for (int i = 1; i < 50; i++) {
            doubleGenerator1 += 20;
            doubleGenerator2 = doubleGenerator1 / 100;
            weightList.add(doubleGenerator2);
        }
        gewicht.setItems(weightList);

        rund.setLabel("Die Katze ist rund");
        rund.setItems(true, false);
        rund.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);


        suess.setLabel("Die Katze ist süß");
        suess.setItems(true, false);
        suess.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
    }

    private HorizontalLayout createBooleanButtons() {

        return new HorizontalLayout(rund, suess);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, cat)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    protected void setCat(Cat cat) {
        this.cat = cat;
        binder.readBean(cat);
    }

    private void validateAndSave() {

        try {
            binder.writeBean(cat);
            fireEvent(new SaveEvent(this, cat));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class CatEditGUIEvent extends ComponentEvent<CatEditGUI> {
        private Cat cat;

        protected CatEditGUIEvent(CatEditGUI source, Cat cat) {
            super(source, false);
            this.cat = cat;
        }

        public Cat getCat() {
            return cat;
        }
    }

    public static class SaveEvent extends CatEditGUIEvent {
        SaveEvent(CatEditGUI source, Cat cat) {
            super(source, cat);
        }
    }

    public static class DeleteEvent extends CatEditGUIEvent {
        DeleteEvent(CatEditGUI source, Cat cat) {
            super(source, cat);
        }

    }

    public static class CloseEvent extends CatEditGUIEvent {
        CloseEvent(CatEditGUI source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
