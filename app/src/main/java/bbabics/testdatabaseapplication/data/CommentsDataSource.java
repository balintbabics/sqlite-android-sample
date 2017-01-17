package bbabics.testdatabaseapplication.data;

/**
 * Created by bbabics on 2017. 01. 17..
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import bbabics.testdatabaseapplication.utils.MySqLiteHelper;

public class CommentsDataSource {

    private SQLiteDatabase database;
    private MySqLiteHelper dbHelper;
    private String[] allColumns = { MySqLiteHelper.COLUMN_ID,
            MySqLiteHelper.COLUMN_COMMENT };

    public CommentsDataSource(Context context) {
        dbHelper = new MySqLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Comment createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySqLiteHelper.COLUMN_COMMENT, comment);
        long insertId = database.insert(MySqLiteHelper.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database.query(MySqLiteHelper.TABLE_COMMENTS,
                allColumns, MySqLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySqLiteHelper.TABLE_COMMENTS, MySqLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<>();

        Cursor cursor = database.query(MySqLiteHelper.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // don't forget to close the cursor
        cursor.close();
        return comments;
    }

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }
}
