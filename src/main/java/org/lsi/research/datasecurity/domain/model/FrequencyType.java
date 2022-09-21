package org.lsi.research.datasecurity.domain.model;

public enum FrequencyType {
    FREQUENTILY_UPDATED(128),FREQUENTILY_REQUEST(128),NONE(192);

    int i ;
    FrequencyType (int i ){
        this.i=i;
    }

    public int getI(){
        return this.i;
    }
}
