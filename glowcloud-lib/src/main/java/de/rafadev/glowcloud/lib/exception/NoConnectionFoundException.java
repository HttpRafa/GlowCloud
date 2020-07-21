package de.rafadev.glowcloud.lib.exception;

//------------------------------
//
// This class was developed by Rafael K.
// On 22.07.2020 at 00:22
// In the project GlowCloud
//
//------------------------------

public class NoConnectionFoundException extends RuntimeException {

    /**
     * Constructs a {@code NoConnectionFoundException} with no detail message.
     */
    public NoConnectionFoundException() {
        super("The system has found no connection");
    }

    /**
     * Constructs a {@code NoConnectionFoundException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public NoConnectionFoundException(String s) {
        super(s);
    }

}
