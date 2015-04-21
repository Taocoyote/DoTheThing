package greendao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import greendao.Time;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table TIME.
*/
public class TimeDao extends AbstractDao<Time, Long> {

    public static final String TABLENAME = "TIME";

    /**
     * Properties of entity Time.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Alert = new Property(1, java.util.Date.class, "alert", false, "ALERT");
        public final static Property Ack = new Property(2, Boolean.class, "ack", false, "ACK");
        public final static Property Note = new Property(3, String.class, "note", false, "NOTE");
        public final static Property Task_id = new Property(4, long.class, "task_id", false, "TASK_ID");
    };

    private DaoSession daoSession;

    private Query<Time> task_TimeListQuery;

    public TimeDao(DaoConfig config) {
        super(config);
    }
    
    public TimeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TIME' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ALERT' INTEGER NOT NULL ," + // 1: alert
                "'ACK' INTEGER," + // 2: ack
                "'NOTE' TEXT," + // 3: note
                "'TASK_ID' INTEGER NOT NULL );"); // 4: task_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TIME'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Time entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getAlert().getTime());
 
        Boolean ack = entity.getAck();
        if (ack != null) {
            stmt.bindLong(3, ack ? 1l: 0l);
        }
 
        String note = entity.getNote();
        if (note != null) {
            stmt.bindString(4, note);
        }
        stmt.bindLong(5, entity.getTask_id());
    }

    @Override
    protected void attachEntity(Time entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Time readEntity(Cursor cursor, int offset) {
        Time entity = new Time( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            new java.util.Date(cursor.getLong(offset + 1)), // alert
            cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0, // ack
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // note
            cursor.getLong(offset + 4) // task_id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Time entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAlert(new java.util.Date(cursor.getLong(offset + 1)));
        entity.setAck(cursor.isNull(offset + 2) ? null : cursor.getShort(offset + 2) != 0);
        entity.setNote(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTask_id(cursor.getLong(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Time entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Time entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "timeList" to-many relationship of Task. */
    public List<Time> _queryTask_TimeList(long task_id) {
        synchronized (this) {
            if (task_TimeListQuery == null) {
                QueryBuilder<Time> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Task_id.eq(null));
                task_TimeListQuery = queryBuilder.build();
            }
        }
        Query<Time> query = task_TimeListQuery.forCurrentThread();
        query.setParameter(0, task_id);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getTaskDao().getAllColumns());
            builder.append(" FROM TIME T");
            builder.append(" LEFT JOIN TASK T0 ON T.'TASK_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Time loadCurrentDeep(Cursor cursor, boolean lock) {
        Time entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Task task = loadCurrentOther(daoSession.getTaskDao(), cursor, offset);
         if(task != null) {
            entity.setTask(task);
        }

        return entity;    
    }

    public Time loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Time> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Time> list = new ArrayList<Time>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Time> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Time> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
