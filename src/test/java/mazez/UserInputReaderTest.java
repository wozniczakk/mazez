package mazez;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static mazez.Mode.FIND_SHORTEST_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserInputReaderTest {

    @Test
    public void gets_generation_params() {
        var out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        var generationParamsInput = "10\n2\nCOLLECT_COINS\n6";
        var in = new ByteArrayInputStream(generationParamsInput.getBytes());
        System.setIn(in);

        var params = new UserInputReader().getGenerationParams();
        assertThat(params.size()).isEqualTo(10);
        assertThat(params.numberOfObstacles()).isEqualTo(2);
    }

    @Test
    public void does_not_allow_too_many_obstacles() {
        var out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        var generationParamsInput = "11\n6\nCOLLECT_COINS\n6";
        var in = new ByteArrayInputStream(generationParamsInput.getBytes());
        System.setIn(in);

        assertThatThrownBy(() -> new UserInputReader().getGenerationParams())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Number of obstacles has to be between 0-5!");
    }

    @Test
    public void gets_coins() {
        var out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        var generationParamsInput = "5";
        var in = new ByteArrayInputStream(generationParamsInput.getBytes());
        System.setIn(in);

        var coins = new UserInputReader().getNumberOfCoins();
        assertThat(coins).isEqualTo(5);
    }

    @Test
    public void gets_mode() {
        var out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        var generationParamsInput = "FIND_SHORTEST_PATH";
        var in = new ByteArrayInputStream(generationParamsInput.getBytes());
        System.setIn(in);

        var modes = new UserInputReader().getModes();
        assertThat(modes).contains(FIND_SHORTEST_PATH);
    }

    @Test
    public void gets_spike_traps() {
        var out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        var generationParamsInput = "3";
        var in = new ByteArrayInputStream(generationParamsInput.getBytes());
        System.setIn(in);

        var traps = new UserInputReader().getNumberOfSpikeTraps();
        assertThat(traps).isEqualTo(3);
    }

    @Test
    public void gets_spider_traps() {
        var out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        var generationParamsInput = "2";
        var in = new ByteArrayInputStream(generationParamsInput.getBytes());
        System.setIn(in);

        var traps = new UserInputReader().getNumberOfSpiderTraps();
        assertThat(traps).isEqualTo(2);
    }

    @Test
    public void does_not_allow_too_many_traps() {
        var out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        var generationParamsInput = "6";
        var in = new ByteArrayInputStream(generationParamsInput.getBytes());
        System.setIn(in);

        assertThatThrownBy(() -> new UserInputReader().getNumberOfSpiderTraps())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Number of traps must be positive and less than 5!");
    }

    @Test
    public void does_not_allow_negative_coins() {
        var out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        var generationParamsInput = "-2";
        var in = new ByteArrayInputStream(generationParamsInput.getBytes());
        System.setIn(in);

        assertThatThrownBy(() -> new UserInputReader().getNumberOfCoins())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Number of coins must be positive!");
    }

    @Test
    public void does_not_allow_invalid_mode() {
        var out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        var generationParamsInput = "test";
        var in = new ByteArrayInputStream(generationParamsInput.getBytes());
        System.setIn(in);

        assertThatThrownBy(() -> new UserInputReader().getModes())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No enum constant mazez.Mode.test");
    }
}
