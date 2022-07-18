import java.io.IOException;

/**
 * Represents a corrupt Appendable object that will always throw an IO exception no matter
 * what methods of it are called.
 */
public class CorruptAppendable implements Appendable {

  /**
   * The empty constructor for the corrupt appendable object.
   */
  public CorruptAppendable() {
    // the constructor should be empty here since there does not need to be any field for this
    // object to work properly
  }

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("happy thursday");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("im tired");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("when is nap time");
  }
}
