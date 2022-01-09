package com.ntncode.restaurantclient.data.roomdb.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ntncode.restaurantclient.data.roomdb.model.CartEntityModel;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CartDAO_Impl implements CartDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CartEntityModel> __insertionAdapterOfCartEntityModel;

  private final EntityDeletionOrUpdateAdapter<CartEntityModel> __deletionAdapterOfCartEntityModel;

  private final EntityDeletionOrUpdateAdapter<CartEntityModel> __updateAdapterOfCartEntityModel;

  public CartDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCartEntityModel = new EntityInsertionAdapter<CartEntityModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `cartTemporal` (`userId`,`userName`,`location`,`email`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CartEntityModel value) {
        if (value.getUserId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getUserId());
        }
        if (value.getUserName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUserName());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLocation());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEmail());
        }
      }
    };
    this.__deletionAdapterOfCartEntityModel = new EntityDeletionOrUpdateAdapter<CartEntityModel>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `cartTemporal` WHERE `userId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CartEntityModel value) {
        if (value.getUserId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getUserId());
        }
      }
    };
    this.__updateAdapterOfCartEntityModel = new EntityDeletionOrUpdateAdapter<CartEntityModel>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `cartTemporal` SET `userId` = ?,`userName` = ?,`location` = ?,`email` = ? WHERE `userId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CartEntityModel value) {
        if (value.getUserId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.getUserId());
        }
        if (value.getUserName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUserName());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLocation());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getEmail());
        }
        if (value.getUserId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getUserId());
        }
      }
    };
  }

  @Override
  public void insertCart(final CartEntityModel users) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCartEntityModel.insert(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteCart(final CartEntityModel itemCart) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCartEntityModel.handle(itemCart);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateCart(final CartEntityModel itemCart) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCartEntityModel.handle(itemCart);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<CartEntityModel> gelAllCart() {
    final String _sql = "Select * from cartTemporal";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
      final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final List<CartEntityModel> _result = new ArrayList<CartEntityModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CartEntityModel _item;
        final Integer _tmpUserId;
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _tmpUserId = null;
        } else {
          _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
        }
        final String _tmpUserName;
        _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
        final String _tmpLocation;
        _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
        final String _tmpEmail;
        _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        _item = new CartEntityModel(_tmpUserId,_tmpUserName,_tmpLocation,_tmpEmail);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
