package com.mjc.maze.mazestructure;

import java.io.Serializable;

public enum MazeTileType implements Serializable {
    Up,
    Down,
    Left,
    Right,

    UpDown,
    UpLeft,
    UpRight,

    DownLeft,
    DownRight,

    LeftRight,

    UpDownLeft,
    UpDownRight,
    UpLeftRight,

    DownLeftRight,

    UpDownLeftRight,

    None
}
