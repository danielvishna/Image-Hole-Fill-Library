import CommandLineUtility.ArgumentParser;
import CommandLineUtility.HoleFillingProcessor;
import org.opencv.core.Core;

public class Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        try {
            ArgumentParser.ProgramArguments programArgs = ArgumentParser.parse(args);
            HoleFillingProcessor processor = new HoleFillingProcessor();
            processor.process(programArgs);
        } catch (IllegalArgumentException e) {
            System.err.println("Input Error: " + e.getMessage());
        }
    }
}