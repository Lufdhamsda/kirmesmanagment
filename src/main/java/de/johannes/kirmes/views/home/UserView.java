package de.johannes.kirmes.views.home;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import de.johannes.kirmes.components.user.UserForm;
import de.johannes.kirmes.data.entity.Role;
import de.johannes.kirmes.data.entity.User;
import de.johannes.kirmes.data.service.RoleService;
import de.johannes.kirmes.data.service.UserService;
import de.johannes.kirmes.views.MainView;

@PageTitle("User | Kirmes CRM")
@Route(value = "user", layout = MainView.class)
@CssImport("./styles/views/home/home.css")
public class UserView extends VerticalLayout {

    private static final String CSS_CLASS_MAIN_LAYOUT = "main-view";
    private static final String CSS_CLASS_USER_GRID = "main-view__user-grid";

    private Grid<User> userGrid;
    private TextField filterText;
    private UserForm userForm;


    private final UserService userService;
    private final RoleService roleService;

    public UserView(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
        init();
    }

    private void init(){
        setSizeFull();
        configureUserGrid();
        configureUserForm();


        add(getToolbar(), getContent());
        updateGrid();
        closeEditor();
    }

    private Component getContent(){
        HorizontalLayout contentLayout = new HorizontalLayout(userGrid, userForm);
        contentLayout.setFlexGrow(2, userGrid);
        contentLayout.setFlexGrow(1, userForm);
        contentLayout.addClassNames("content");
        contentLayout.setSizeFull();
        return contentLayout;
    }

    private void configureUserGrid(){
        this.userGrid = new Grid<>();
        userGrid.addClassNames(CSS_CLASS_USER_GRID);
        userGrid.setSizeFull();
        userGrid.addColumn(User::getFirstName).setHeader("Firstname");
        userGrid.addColumn(User::getLastName).setHeader("Lastname");
        userGrid.addColumn(User::getEmail).setHeader("Email");
        userGrid.addColumn(User::getPhoneNumber).setHeader("Phone Number");
        userGrid.addColumn(User::getLocation).setHeader("Location");
        userGrid.addComponentColumn(user -> {
            ComboBox<Role> roleComboBox = new ComboBox<>();
            roleComboBox.setItems(roleService.getAllRoles());
            roleComboBox.setItemLabelGenerator(Role::getName);
            roleComboBox.setValue(user.getRole());

            return roleComboBox;
        });


        userGrid.asSingleSelect().addValueChangeListener(event -> {
            editUser(event.getValue());
        });
    }

    private void configureUserForm(){
        userForm = new UserForm();
        userForm.setWidth("25%");

        userForm.addSaveListener(this::saveUser);
        userForm.addDeleteListener(this::deleteUser);
        userForm.addCloseListener(event -> closeEditor());
    }

    private HorizontalLayout getToolbar() {
        filterText = new TextField();
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(click -> addContact());

        var toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
    
    private void updateGrid(){
        userGrid.setItems(userService.getUsersWithFilter(filterText.getValue()));
    }

    public void editUser(User user){
        if (user == null) {
            closeEditor();
        } else {
            userForm.setUser(user);
            userForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor(){
        userForm.setUser(null);
        userForm.setVisible(false);
        removeClassName("editing");
    }

    private void addContact(){
        userGrid.asSingleSelect().clear();
        editUser(new User());
    }

    private void saveUser(UserForm.SaveEvent event){
        userService.save(event.getUser());
        updateGrid();
    }

    private void deleteUser(UserForm.DeleteEvent event){
        userService.delete(event.getUser());
        updateGrid();
    }

}
