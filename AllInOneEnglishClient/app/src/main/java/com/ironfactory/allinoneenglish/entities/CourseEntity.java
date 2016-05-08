package com.ironfactory.allinoneenglish.entities;

import java.util.Date;

/**
 * Created by IronFactory on 2016. 4. 14..
 */
public class CourseEntity {
    private int index;
    private Date lastStudyDate;
    private boolean bookmark;

    public CourseEntity() {
    }

    public CourseEntity(int index, Date lastStudyDate, boolean bookmark) {
        this.index = index;
        this.lastStudyDate = lastStudyDate;
        this.bookmark = bookmark;
    }

    public Date getLastStudyDate() {
        return lastStudyDate;
    }

    public void setLastStudyDate(Date lastStudyDate) {
        this.lastStudyDate = lastStudyDate;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
