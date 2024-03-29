import java.util.Arrays;
import java.util.Enumeration;

/**
 * Enumerates all values of the mixed radix base given as parameter.
 * 
 * @link http://en.wikipedia.org/wiki/Mixed_radix
 * 
 */
public class MixedRadixEnumeration implements Enumeration<int[]> {
  private int[] base;
  private int[] current;
  private boolean isLast = false;

  /**
   * @param base the base
   */
  public MixedRadixEnumeration(int[] base) {
    super();
    if (base == null) {
      throw new IllegalArgumentException("Utilities.MixedRadixEnumeration - null base array");
    }
    this.base = Arrays.copyOf(base, base.length);

    for (int i = 0; i < base.length; i++) {
      if (base[i] < 1) {
        throw new IllegalArgumentException("Utilities.MixedRadixEnumeration - non-positive base");
      }
    }
    current = null;
  }

  @Override
  public boolean hasMoreElements() {
    if (current == null) {
      return true;
    }
    if (isLast) {
      return false;
    }

    boolean o = false;
    for (int i = 0; i < base.length; i++) {
      if (current[i] < (base[i] - 1)) {
        o = true;
        break;
      }
    }
    if (!o) {
      isLast = true;
    }
    return o;
  }

  @Override
  public int[] nextElement() {
    if (!hasMoreElements()) {
      throw new IllegalStateException(
          "Utilities.MixedRadixEnumeration - call on nextElement, but there's no next element!");
    }

    int[] o = new int[base.length];
    if (current == null) {
      for (int i = 0; i < base.length; i++) {
        o[i] = 0;
      }
    } else {
      o = Arrays.copyOf(current, current.length);
      for (int i = 0; i < base.length; i++) {
        if (o[i] < (base[i] - 1)) {
          o[i]++;
          break;
        } else {
          o[i] = 0;
        }
      }
    }
    current = Arrays.copyOf(o, o.length);

    return o;
  }

  public static int[] mapIndexToCoordinates(int index, int[] base) {
    int[] o = new int[base.length];
    int t = index;

    for (int k = base.length - 1; k >= 0; k--) {
      int b = base[k];
      o[k] = t % b;
      t = t / b;
    }
    return o;
  }
  
}
