package de.johannes.kirmes.components.user;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import de.johannes.kirmes.components.custom.fields.PhoneField;
import de.johannes.kirmes.data.entity.User;

public class UserForm extends FormLayout {

	private static final String CSS_CLASS_USER_FORM = "user-form";

	private final Binder<User> binder = new BeanValidationBinder<>(User.class);

	public UserForm(){
		addClassNames(CSS_CLASS_USER_FORM);
		init();
	}

	private void init(){
		createFields();
		add(createButtonLayout());
	}
	private void createFields(){
		TextField firstnameField = new TextField("First Name");
		TextField lastnameField = new TextField("Last Name");
		DatePicker birthdayField = new DatePicker("Birthday");
		EmailField emailFieldField = new EmailField("Email");
		TextField locationField = new TextField("Location");
		PhoneField phoneField = new PhoneField();


		binder.bind(firstnameField, User::getFirstName, User::setFirstName);
		binder.bind(lastnameField, User::getLastName, User::setLastName);
		binder.bind(birthdayField, User::getBirthday, User::setBirthday);
		binder.bind(emailFieldField, User::getEmail, User::setEmail);
		binder.bind(locationField, User::getLocation, User::setLocation);
		binder.bind(phoneField.getPhoneNumberField(), User::getPhoneNumber, User::setPhoneNumber);

		add(firstnameField, lastnameField, birthdayField, emailFieldField, locationField, phoneField);
	}

	private HorizontalLayout createButtonLayout(){
		Button saveButton = new Button("Save");
		Button deleteButton = new Button("Delete");
		Button closeButton = new Button("Close");

		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
		closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		saveButton.addClickShortcut(Key.ENTER);
		closeButton.addClickShortcut(Key.ESCAPE);

		saveButton.addClickListener(event -> validateAndSave());
		deleteButton.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
		closeButton.addClickListener(event -> fireEvent(new CloseEvent(this)));

		return new HorizontalLayout(saveButton, deleteButton, closeButton);
	}

	public void setUser(User user){
		binder.setBean(user);
	}

	private void validateAndSave(){
		if (binder.isValid()){
			fireEvent(new SaveEvent(this, binder.getBean()));
		}
	}



	public abstract static class UserFormEvent extends ComponentEvent<UserForm> {

		private User user;

		protected UserFormEvent(UserForm source, User user) {
			super(source, false);
			this.user = user;
		}


		public User getUser() {
			return user;
		}
	}

	public static class SaveEvent extends UserFormEvent {
		SaveEvent(UserForm source, User contact) {
			super(source, contact);
		}
	}

	public static class DeleteEvent extends UserFormEvent {
		DeleteEvent(UserForm source, User contact) {
			super(source, contact);
		}

	}

	public static class CloseEvent extends UserFormEvent {
		CloseEvent(UserForm source) {
			super(source, null);
		}
	}

	public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
		return addListener(DeleteEvent.class, listener);
	}

	public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
		return addListener(SaveEvent.class, listener);
	}
	public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
		return addListener(CloseEvent.class, listener);
	}

}
