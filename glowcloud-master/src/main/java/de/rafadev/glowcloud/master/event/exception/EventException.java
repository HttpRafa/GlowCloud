package de.rafadev.glowcloud.master.event.exception;

//------------------------------
//
// This class was developed by Rafael K.
// On 22.07.2020 at 12:16
// In the project GlowCloud
//
//------------------------------

public class EventException extends RuntimeException {

    /**
     * Constructs a {@code EventException} with no detail message.
     */
    public EventException() {
        super("Can`t call the event");
    }

    /**
     * Constructs a {@code EventException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public EventException(String s) {
        super(s);
    }

}