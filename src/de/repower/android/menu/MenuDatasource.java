package de.repower.android.menu;

import android.database.Cursor;
import android.database.SQLException;

public interface MenuDatasource {

    public static final String KEY_DATE = "menu_date";
    public static final String KEY_BODY = "body";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_PRICE = "price";
    public static final String KEY_ROWID = "_id";

    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param title the title of the note
     * @param body the body of the note
     * @return rowId or -1 if failed
     */
    public abstract long createNote(String title, String body);

    /**
     * Delete the note with the given rowId
     * 
     * @param rowId id of note to delete
     * @return true if deleted, false otherwise
     */
    public abstract boolean deleteNote(long rowId);

    /**
     * Return a Cursor over the list of all notes in the database
     * 
     * @return Cursor over all notes
     */
    public abstract Cursor fetchAllNotes();

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     * 
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public abstract Cursor fetchNote(long rowId) throws SQLException;

    /**
     * Update the note using the details provided. The note to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     * 
     * @param rowId id of note to update
     * @param title value to set note title to
     * @param body value to set note body to
     * @return true if the note was successfully updated, false otherwise
     */
    public abstract boolean updateNote(long rowId, String title, String body);

}