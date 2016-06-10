package application.model;		// Package that the class is contained in


/*
 * Import Section
 */
import java.text.Normalizer;	// Used to normalize text
import java.util.regex.Pattern;		// Used to find text patterns
import java.util.stream.Stream;		// Used to keep the data updated

import javafx.collections.FXCollections;	// Precursor for observable lists
import javafx.collections.ObservableList;	// JavaFX array list class
import javafx.event.Event;					// Used for event handling
import javafx.scene.control.ComboBox;		// GUI class for combo box
import javafx.scene.control.Tooltip;		// Makes a popup on a combobox
import javafx.scene.input.KeyCode;			// Used for finding key values
import javafx.scene.input.KeyEvent;			// Used for keyboard input
import javafx.stage.Window;					// Used for window handling 

/**
 * 
 * Uses a combobox tool tip as the suggestion for auto complete and updates the
 * combo box items accordingly
 * It does not work with space, space and escape cause the combobox to hide and
 * clean the filter
 * 
 *
 * @param <T>
 */
public class ComboBoxAutoComplete<T> 
{
	/*
	 * Instance Variables
	 */
	private ComboBox<T> cmb;	// The combo box to be edited
	String filter = "";			// The parameter to filter in the list
	private ObservableList<T> originalItems;	// The items that are originally in the list

	public ComboBoxAutoComplete(ComboBox<T> cmb) 
	/*
	 * Precondition:  User has entered data into a combo box
	 * Postcondition: Displays a drop down menu with possible suggestions
	 */
	{
		this.cmb = cmb;			
		originalItems = FXCollections.observableArrayList(cmb.getItems());
		cmb.setTooltip(new Tooltip());
		cmb.setOnKeyPressed(this::handleOnKeyPressed);
		cmb.setOnHidden(this::handleOnHiding);
	}

	public void handleOnKeyPressed(KeyEvent e) 
	/*
	 * Precondition:  User presses a key or changes the text
	 * Postcondition: Adds the key pressed to the filter
	 */
	{
		/*
		 * ObservableList for the filtered options
		 */
		ObservableList<T> filteredList = FXCollections.observableArrayList();
		
		KeyCode code = e.getCode();		// Gets the value of the last key entered

		/*
		 * If the key pressed is a letter key
		 */
		if (code.isLetterKey()) 
		{
			filter += e.getText();
		}
		/*
		 * If key is a backspace and there is something to delete
		 */
		if (code == KeyCode.BACK_SPACE && filter.length() > 0) 
		{
			filter = filter.substring(0, filter.length() - 1);
			cmb.getItems().setAll(originalItems);
		}
		/*
		 * If escape key is pressed get rid of suggestions
		 */
		if (code == KeyCode.ESCAPE) 
		{
			filter = "";
		}
		/*
		 * If key code is a space
		 */
		if (code == KeyCode.SPACE)
		{
			filter += e.getText();
		}
		/*
		 * If there is no filter
		 */
		if (filter.length() == 0) 
		{
			filteredList = originalItems;
			cmb.getTooltip().hide();
		} 
		/*
		 * Else there is characters in the filter
		 */
		else 
		{
			Stream<T> itens = cmb.getItems().stream();
			String txtUsr = unaccent(filter.toString().toLowerCase());
			itens.filter(el -> unaccent(el.toString().toLowerCase()).contains(txtUsr)).forEach(filteredList::add);
			cmb.getTooltip().setText(txtUsr);
			Window stage = cmb.getScene().getWindow();
			double posX = stage.getX() + cmb.getBoundsInParent().getMinX();
			double posY = stage.getY() + cmb.getBoundsInParent().getMinY();
			cmb.getTooltip().show(stage, posX, posY);
			cmb.show();
		}
		cmb.getItems().setAll(filteredList);
	}

	public void handleOnHiding(Event e) 
	/*
	 * Precondition:  User wants to hide the drop down menu
	 * Postcondition: Suggestion box is hidden from the screen
	 */
	{
		filter = "";
		cmb.getTooltip().hide();
		T s = cmb.getSelectionModel().getSelectedItem();
		cmb.getItems().setAll(originalItems);
		cmb.getSelectionModel().select(s);
	}

	private String unaccent(String s) 
	/*
	 * Precondition:  Text is inputed and isn't in a proper format
	 * Postcondition: Text format is changed so it can be used by the suggestion box
	 */
	{
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");	// Return string pattern
	}

}