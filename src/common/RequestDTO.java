package common;


import common.enums.RequestType;

public class RequestDTO {

    private RequestType requestType = RequestType.EMPTY;
    private String value1 = "";
    private String value2 = "";
    private String value3 = "";
    private String value4 = "";
    private String value5 = "";


    public RequestDTO() {
    }
    public RequestDTO(RequestType requestType) {
        this.requestType = requestType;
    }
    public RequestDTO(RequestType requestType,
                      String value) {
        this.requestType = requestType;
        this.value1 = value;
    }
    public RequestDTO(RequestType requestType,
                      String value1, String value2) {
        this.requestType = requestType;
        this.value1 = value1;
        this.value2 = value2;
    }
    public RequestDTO(RequestType requestType,
                      String value1, String value2, String value3) {
        this.requestType = requestType;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }
    public RequestDTO(RequestType requestType,
                      String value1, String value2, String value3, String value4) {
        this.requestType = requestType;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
    }
    public RequestDTO(RequestType requestType,
                      String value1, String value2, String value3, String value4, String value5) {
        this.requestType = requestType;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
    }


    public String toString() {
        return "["+requestType.name()+","+value1+","+value2+","+value3+","+value4+","+value5+"]";
    }


    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
    public void setValue1(String value1) {
        this.value1 = value1;
    }
    public void setValue2(String value2) {
        this.value2 = value2;
    }
    public void setValue3(String value3) {
        this.value3 = value3;
    }
    public void setValue4(String value4) {
        this.value4 = value4;
    }
    public void setValue5(String value5) {
        this.value5 = value5;
    }

    public RequestType getRequestType() {
        return requestType;
    }
    public String getValue1() {
        return value1;
    }
    public String getValue2() {
        return value2;
    }
    public String getValue3() {
        return value3;
    }
    public String getValue4() {
        return value4;
    }
    public String getValue5() {
        return value5;
    }

    public void reset() {
        requestType = RequestType.EMPTY;
        value1 = "";
        value2 = "";
        value3 = "";
        value4 = "";
        value5 = "";
    }
}
