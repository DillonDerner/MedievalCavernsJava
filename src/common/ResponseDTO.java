package common;

import common.enums.ResponseType;

public class ResponseDTO {

    private ResponseType responseType = ResponseType.ERROR;
    private String value1 = "";
    private String value2 = "";
    private String value3 = "";
    private String value4 = "";
    private String value5 = "";


    public ResponseDTO() {
    }
    public ResponseDTO(ResponseType responseType) {
        this.responseType = responseType;
    }
    public ResponseDTO(ResponseType responseType,
                       String value) {
        this.responseType = responseType;
        this.value1 = value;
    }
    public ResponseDTO(ResponseType responseType,
                       String value1, String value2) {
        this.responseType = responseType;
        this.value1 = value1;
        this.value2 = value2;
    }
    public ResponseDTO(ResponseType responseType,
                       String value1, String value2, String value3) {
        this.responseType = responseType;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }
    public ResponseDTO(ResponseType responseType,
                       String value1, String value2, String value3, String value4) {
        this.responseType = responseType;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
    }
    public ResponseDTO(ResponseType responseType,
                       String value1, String value2, String value3, String value4, String value5) {
        this.responseType = responseType;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
    }


    public String toString() {
        return "["+responseType.name()+","+value1+","+value2+","+value3+","+value4+","+value5+"]";
    }


    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
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

    public ResponseType getResponseType() {
        return responseType;
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
        responseType = ResponseType.ERROR;
        value1 = "";
        value2 = "";
        value3 = "";
        value4 = "";
        value5 = "";
    }
}
