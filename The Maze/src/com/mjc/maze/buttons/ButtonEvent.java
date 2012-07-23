package com.mjc.maze.buttons;

import java.io.Serializable;
import java.util.EventObject;

public class ButtonEvent extends EventObject  implements Serializable
{

    private static final long serialVersionUID = 1L;
    private ButtonEventType Type;

    public ButtonEvent(Object source, ButtonEventType type)
    {
        super(source);
        Type = type;
    }

    public ButtonEventType getType()
    {
        return Type;
    }

}
