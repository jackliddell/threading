package belley;

/**
 * Created by jiri.peinlich on 26/10/2016.
 */
public class TestClass
{

    boolean characterDuplicity( String input ) {

        int[] counters = new int[26];

        for( int i = 0; i < input.length(); i++ ) {
            char charAt = input.toLowerCase().charAt( i );

            counters[charAt - 'a']++;

        }

        for( int counter : counters ) {
            if( counter >= 2 ) {
                return true;
            }
        }
        return false;
    }

    public String reverse( String input ) {
        char[] inputArray = input.toCharArray();
        char[] result = new char[inputArray.length];

        for( int i = 0; i < inputArray.length; i++ ) {
            char c = inputArray[i];
            int pos = result.length - i - 1;
            result[pos] = c;
        }

        return new String( result );
    }
}
