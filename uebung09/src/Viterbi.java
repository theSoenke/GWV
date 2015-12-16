import java.util.HashMap;

public class Viterbi
{
    private HMM _hmm;
    private HashMap<String,Integer> start_word = new HashMap<String,Integer>();
    
    public Viterbi(HMM hmm)
    {
        _hmm = hmm;
    }
    
    // TODO Satz taggen
    public String mostLikelySequence(String sentence)
    {
        String[] words = sentence.split("\\s+");
        StringBuffer buffer = new StringBuffer();
        String tag="";
        
        
        for(int i = 0; i < words.length; i++)
        {
            // TODO get tag of words[i]
            Word word= new Word(words[i]);
           
            Tag ta = new Tag(tag);
            word.addTag(ta);
            tag = word.getTags().toString();
            buffer.append(words[i] + " " + tag);
            System.out.println(words[i]  + " " + tag);
        }
        
        return buffer.toString();
    }
}
