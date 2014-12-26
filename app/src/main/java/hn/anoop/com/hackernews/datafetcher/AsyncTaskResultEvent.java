package hn.anoop.com.hackernews.datafetcher;

import javax.xml.transform.Result;

import hn.anoop.com.hackernews.model.Item;

/**
 * Created by Akunju00c on 12/2/2014.
 */
public class AsyncTaskResultEvent {


    ResultType resultType;

    private Object result;

    public AsyncTaskResultEvent(ResultType resultType, Object result) {
        this.resultType = resultType;
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public ResultType getResultType(){
        return resultType;
    }

    public enum ResultType {
        ID,
        ITEM
    }
}

