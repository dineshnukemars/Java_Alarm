package com.sky.utility.alarm

import java.io.File
import java.io.IOException

import javax.sound.sampled.*
import javax.swing.JOptionPane


fun main() {

    val waitTime: Long = 10000
    val audioFilePath = "D:\\WORKSPACE\\IdeaProjects\\Alarm\\audio\\sound.wav"

    val mins = JOptionPane.showInputDialog("Enter mins for alarm").toInt()
    println("time - $mins mins")

    val milliSecs = mins * 60 * 1000
    var remaining = milliSecs.toLong()

    while (remaining > 0) {

        Thread.sleep(waitTime)
        remaining -= waitTime

        printTimeLog(milliSecs, remaining)
    }

    PlayAlarm().play(audioFilePath)
}

private fun printTimeLog(milliSecs: Int, remaining: Long) {
    val passedMills = milliSecs - remaining
    val passedTimeTxt = "passed ${passedMills / (1000 * 60)} mins ${(passedMills / 1000) % 60} secs"
    val remainingTimeTxt = "time remaining  ${remaining / (1000 * 60)} mins ${(remaining / 1000) % 60} secs"
    println("$passedTimeTxt, $remainingTimeTxt")
}

class PlayAlarm {
    var playCompleted = false

    fun play(audioFilePath: String?) {
        val audioFile = File(audioFilePath)
        try {
            val audioStream = AudioSystem.getAudioInputStream(audioFile)
            val info = DataLine.Info(Clip::class.java, audioStream.format)
            val audioClip = AudioSystem.getLine(info) as Clip
            audioClip.addLineListener {
                val type: LineEvent.Type = it.type

                if (type === LineEvent.Type.START) {
                    println("Playback started.")
                } else if (type === LineEvent.Type.STOP) {
                    playCompleted = true
                    println("Playback completed.")
                }
            }
            audioClip.open(audioStream)
            audioClip.start()
            while (!playCompleted) {
                // wait for the playback completes
                try {
                    Thread.sleep(1000)
                } catch (ex: InterruptedException) {
                    ex.printStackTrace()
                }
            }
            audioClip.close()
        } catch (ex: UnsupportedAudioFileException) {
            println("The specified audio file is not supported.")
            ex.printStackTrace()
        } catch (ex: LineUnavailableException) {
            println("Audio line for playing back is unavailable.")
            ex.printStackTrace()
        } catch (ex: IOException) {
            println("Error playing the audio file.")
            ex.printStackTrace()
        }
    }

}