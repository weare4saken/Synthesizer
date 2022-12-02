import javax.sound.midi.*;
import java.util.Scanner;
public class Main {

    private static final byte C = 60; // do
    private static final byte D = 62; // re
    private static final byte E = 64; // mi
    private static final byte F = 65; // fa
    private static final byte G = 67; // sol
    private static final byte A = 69; // la
    private static final byte B = 70; // si
    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
      System.out.println("Please enter some notes: ");

        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine(); // A a B C...
        System.out.println("You entered: " + text);

        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();

        Receiver receiver = synthesizer.getReceiver();

        receiver.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE, 31, 100), -100);

        text = text.toUpperCase().trim();
        while (!text.equals("EXIT")) {
            byte noteId = convertToId(text);
            playNote(receiver, noteId);
            text = scanner.nextLine().toUpperCase().trim();
        }

        synthesizer.close();

      scanner.close();
    }

    private static void playNote(Receiver receiver, byte noteId) throws InvalidMidiDataException, InterruptedException {
        ShortMessage msg = new ShortMessage();
        msg.setMessage(ShortMessage.NOTE_ON, noteId, 100);

        receiver.send(msg, -100);

        Thread.sleep(1000); // 1 second

        msg.setMessage(ShortMessage.NOTE_OFF, noteId, 100);
    }

    private static byte convertToId(String text) { // A a "A   "
        String note = text.toUpperCase().trim();

        switch (note) {
            case "A":
                return A;
            case "B":
                return B;
            case "C":
                return C;
            case "D":
                return D;
            case "E":
                return E;
            case "F":
                return F;
            case "G":
                return G;
            default:
                System.out.println("You entered incorrect note: " + text);
                return C;
        }
    }
}