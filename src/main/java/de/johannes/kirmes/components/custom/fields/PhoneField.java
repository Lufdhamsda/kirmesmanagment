package de.johannes.kirmes.components.custom.fields;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

/**
 * @author j.henkel
 */
public class PhoneField extends HorizontalLayout
		implements HasValue<AbstractField.ComponentValueChangeEvent<PhoneField, String>, String> {

	private TextField phoneNumberField;
	private ComboBox<String> countryCodeComboBox;
	private HashMap<String, String> countryCodeMapping;


	public PhoneField() {
		setSpacing(true);
		countryCodeMapping = new HashMap<>();
		createMapping();
		init();
	}


	private void init() {
		createCountryCodeComboBox();
		createPhoneNumberField();
	}


	private void createPhoneNumberField() {
		phoneNumberField = new TextField("Phone number");
		phoneNumberField.setPlaceholder("Enter phone number");
		phoneNumberField.setRequiredIndicatorVisible(false);

		add(phoneNumberField);
	}


	private void createCountryCodeComboBox() {
		countryCodeComboBox = new ComboBox<>("Country code");
		countryCodeComboBox.setItems(Arrays.asList("US", "GB", "DE", "FR"));
		countryCodeComboBox.setRequiredIndicatorVisible(false);
		countryCodeComboBox.addValueChangeListener(event -> {
			phoneNumberField.setErrorMessage(null);
			phoneNumberField.setPattern(getPhonePattern(event.getValue()));
			if (phoneNumberField.isEmpty()) {
				phoneNumberField.setValue(countryCodeMapping.get(event.getValue()));
			}
		});

		add(countryCodeComboBox);
	}


	public String getValue() {
		return phoneNumberField.getValue() == null ? "" : phoneNumberField.getValue();
	}

	@Override
	public void setValue(String value) {
		phoneNumberField.setValue(value == null ? "" : value);
		setCountryCode();
	}


	@Override
	public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
		phoneNumberField.setRequiredIndicatorVisible(requiredIndicatorVisible);
		countryCodeComboBox.setRequiredIndicatorVisible(requiredIndicatorVisible);
	}

	@Override
	public boolean isRequiredIndicatorVisible(){
		return phoneNumberField.isRequiredIndicatorVisible();
	}

	@Override
	public void setReadOnly(boolean readOnly){
		phoneNumberField.setReadOnly(readOnly);
		countryCodeComboBox.setReadOnly(readOnly);
	}

	@Override
	public boolean isReadOnly(){
		return phoneNumberField.isReadOnly();
	}

	@Override
	public Registration addValueChangeListener(
			ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<PhoneField, String>> valueChangeListener) {
		return null;
	}

	private String getPhonePattern(String countryCode) {
		return switch (countryCode) {
			case "US" -> "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$"; // US phone number pattern
			case "GB" ->
					"^(?:(?:\\(?(?:0(?:0|11)\\)?[\\s-]?\\(?|\\+)44\\)?[\\s-]?(?:\\(?0\\)?[\\s-]?)?)|(?:\\(?0))(?:(?:\\d{5}\\)?[\\s-]?\\d{4,5})|(?:\\d{4}\\)?[\\s-]?(?:\\d{5}|\\d{3}[\\s-]?\\d{3})|\\d{3}\\)?[\\s-]?\\d{3}[\\s-]?\\d{3,4}|\\d{2}\\)?[\\s-]?(?:\\d{4}[\\s-]?\\d{4}|\\d{5}[\\s-]?\\d{3})|\\d{1}\\)?[\\s-]?\\d{3}[\\s-]?\\d{4,5}|\\d{6}[\\s-]?\\d{3})$"; // UK phone number pattern
			case "DE" -> "^\\+49\\d{10}$"; // Germany phone number pattern
			case "FR" -> "^\\+33\\d{9}$"; // France phone number pattern
			default -> ""; // Default pattern
		};
	}

	private void createMapping(){
		countryCodeMapping.put("US", "+1");
		countryCodeMapping.put("GB", "+44");
		countryCodeMapping.put("DE", "+49");
		countryCodeMapping.put("FR", "+33");
	}

	public void setCountryCode() {
		String phoneNumber = phoneNumberField.getValue();
		for (Map.Entry<String, String> entry : countryCodeMapping.entrySet()) {
			String countryCode = entry.getKey();
			String prefix = entry.getValue();
			if (phoneNumber.startsWith(prefix)) {
				countryCodeComboBox.setValue(countryCode);
				break;
			}
		}
	}

	public TextField getPhoneNumberField(){
		return phoneNumberField;
	}


}
