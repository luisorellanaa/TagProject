package tagProject.address.model.ranking;

import org.terrier.matching.models.WeightingModel;
import org.terrier.matching.models.WeightingModelLibrary;

public class MyRankingModel extends WeightingModel{
	
	private static final long serialVersionUID = 1L;

	/** model name */
	private static final String name = "MyRankingModel";

	/** The constant k_1.*/
	private double k_1 = 1.2d;
	
	/** The constant b.*/
	private double b = 0.75d;

	/** 
	 * A default constructor to make this model.
	 */
	public MyRankingModel() {
		super();
	}
	/** 
	 * Constructs an instance of TF_IDF
	 * @param _b
	 */
	public MyRankingModel(double _b) {
		this();
		this.b = _b;
	}

	/**
	 * Returns the name of the model, in this case "TF_IDF"
	 * @return the name of the model
	 */
	public final String getInfo() {
		return name;
	}
	/**
	 * Uses TF_IDF to compute a weight for a term in a document.
	 * @param tf The term frequency of the term in the document
	 * @param docLength the document's length
	 * @return the score assigned to a document with the given 
	 *		 tf and docLength, and other preset parameters
	 */
	public final double score(double tf, double docLength) {
		double Robertson_tf = k_1*tf/(tf+k_1*(1-b+b*docLength/averageDocumentLength));
		double idf = WeightingModelLibrary.log(numberOfDocuments/documentFrequency+1);
		//crear identificador hash a partir de la cantidad de caracteres
		return keyFrequency * Robertson_tf * idf /* *val_eti*/;
	}

	/**
	 * Sets the b parameter to ranking formula
	 * @param _b the b parameter value to use.
	 */
	public void setParameter(double _b) {
		this.b = _b;
	}


	/**
	 * Returns the b parameter to the ranking formula as set by setParameter()
	 */
	public double getParameter() {
		return this.b;
	}
	
    /**
     * Returns a hash code for this string. The hash code for a
     * {@code String} object is computed as
     * <blockquote><pre>
     * s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
     * </pre></blockquote>
     * using {@code int} arithmetic, where {@code s[i]} is the
     * <i>i</i>th character of the string, {@code n} is the length of
     * the string, and {@code ^} indicates exponentiation.
     * (The hash value of the empty string is zero.)
     *
     * @return  a hash code value for this object.
     */
    /*public int hashCode() {
        int h = hash;
        if (h == 0 && value.length > 0) {
            char val[] = value;

            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
            hash = h;
        }
        return h;
    }*/
}
