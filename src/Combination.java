import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Set;
import java.io.Serializable;

/**
 * 
 * @author Nicolas Couture-Grenier
 * 
 */
public class Combination extends BitSet implements Comparable<Combination>, Serializable {

  private static final long serialVersionUID = 1L;
  protected int m_n;

  public Combination(Integer p_n) {
    super(p_n);
    m_n = p_n;
  }

  public int getN() {
    return m_n;
  }

  public int getK() {
    return this.cardinality();
  }

  public Combination(Integer p_n, Set<Integer> p_s) {
    this(p_n);
    for (int i = 0; i < p_n; i++) {
      this.set(i, p_s.contains(i));
    }
  }

  public Combination(Combination c) {
    this(c.m_n);
    this.or(c);
  }

  public Combination(BitSet c, int n) {
    this(n);
    this.or(c);
  }


  public Combination symmetricDifference(Combination y) {
    BitSet x = new BitSet(12);
    x.or(this);
    x.xor(y);
    int n = Math.max(this.getN(), y.getN());
    return new Combination(x, n);
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + m_n;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    return this.compareTo((Combination) obj) == 0;
  }

  @Override
  public String toString() {

    String output = "";
    for (int i = nextSetBit(0); i >= 0; i = nextSetBit(i + 1)) {
      output += Integer.toString(i) + ", ";
    }
    if (!output.isEmpty()) {
      output = output.substring(0, output.length() - 2);
    }

    return "{" + output + "}";
  }
  public ArrayList<Integer> asSequence(){
    ArrayList<Integer> o = new ArrayList<Integer>();
    for (int i = nextSetBit(0); i >= 0; i = nextSetBit(i + 1)) {
      o.add(i);
    }
    return o;
    
  }
  @Override
  public int compareTo(Combination o) {
    if (this.m_n < o.m_n) {
      return -1;
    }
    if (this.m_n > o.m_n) {
      return 1;
    }

    BitSet a = new BitSet();
    a.or(this);
    BitSet b = new BitSet();
    b.or(o);
    BitSet c = new BitSet();
    c.or(this);

    c.xor(b);

    int i = c.nextSetBit(0);

    if (i == -1) {
      return 0;
    } else {
      return b.get(i) ? -1 : 1;
    }
  }

  /**
   * Lists all combinations that have 1 more element than this one.
   * 
   * @return
   */
  public static List<Combination> refinements(Combination c) {
    int n = c.getN() - c.getK();
    if (n == 0) {
      return null;
    }
    List<Combination> o = new ArrayList<Combination>();
    int k = 0;
    for (int i = 0; i < n; i++) {
      while (c.get(k)) {
        k++;
      }
      BitSet b = new BitSet();
      b.or(c);
      b.set(k++);
      o.add(new Combination(b, c.getN()));
    }
    return o;
  }

  public static Combination merge(Combination a, Combination b) {
    BitSet x = new BitSet();
    x.or(a);
    x.or(b);
    return new Combination(x, Math.max(a.getN(), b.getN()));
  }


  public Combination rotate(int t) {
    int k = t;
    while (k < 0) {
      k += m_n;
    }
    while (k >= m_n) {
      k -= m_n;
    }

    BitSet x = new BitSet(m_n);

    for (int i = 0; i < m_n; i++) {
      x.set(i, this.get((i - k + m_n) % m_n));
    }

    return new Combination(x, m_n);

  }
  public Combination minus(Combination c){
    Combination o = new Combination(this);
    int n = Math.min(getN(), c.getN());
    for(int i=0;i<n;i++){
      if(o.get(i) && c.get(i)){
        o.set(i,false);
      }
    }
    return o;
  }



}
