package com.dcelik.myapplicationlistview;

/**
 * Created by dcelik on 9/18/14.
 */
public class Chat {
    String id, name, time, message;
    //byte[] image;

    //Public Constructor to create a kitty
    //public Chat(String id, String name, String time, String message, byte[] image){
    public Chat(String id, String name, String time, String message){
        this.id = id;
        this.name = name;
        this.time = time;
        this.message = message;
        //this.image = image;
    }

    /**
     * Get Fields
     */
    public String getId(){
        return this.id;
    }

    public String getName() { return this.name;}

    public String getMessage() {return this.message;}

    public String getTime() {return this.time;}

    /**
     * Set Fields
     */
    public void setName(String value){
        this.name = toTitleCase(value);
    }

    public void setMessage(String value){
        this.message = toTitleCase(value);
    }

    /**
     * Title Case Method
     */
    public String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
