package com.longshihan.sqlite;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.longshihan.sqlite.Clip;

import com.longshihan.sqlite.ClipDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig clipDaoConfig;

    private final ClipDao clipDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        clipDaoConfig = daoConfigMap.get(ClipDao.class).clone();
        clipDaoConfig.initIdentityScope(type);

        clipDao = new ClipDao(clipDaoConfig, this);

        registerDao(Clip.class, clipDao);
    }
    
    public void clear() {
        clipDaoConfig.getIdentityScope().clear();
    }

    public ClipDao getClipDao() {
        return clipDao;
    }

}
