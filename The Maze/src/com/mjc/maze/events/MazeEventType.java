package com.mjc.maze.events;

import java.io.Serializable;

public enum MazeEventType  implements Serializable
{
onLevelCompleted,
stoppedMoving,
arrivedAtFinish,
timerGoesOff,
obtainedHintGiver,
obtainedTimeExtender,
obtainedTeleporter,
animationCompleted,
screenChanged,
}
