import java.util.*;

@SuppressWarnings("unchecked")
public class SetMergerImpl implements SetMerger {

    @Override
    public Set<Set> merge(Set superset) {

        ArrayList<Set> result = new ArrayList<>();
        for(Object elem : superset) {
            result.add(new HashSet((Set)elem));
        }

        for(int i = 0; i < result.size(); i++) {
            for(int j = 0; j < result.size(); j++) {

                if(i == j) {
                    continue;
                }

                Set dst = result.get(i);
                Set src = result.get(j);

                if(!Collections.disjoint(dst, src)) {
                    dst.addAll(src);
                }

            }
        }

        return new HashSet<>(result); // make distinct
    }

    @Override
    public Map merge(Map supermap) {

        Map<Object, Set> result = new LinkedHashMap<>();
        for(Object key : supermap.keySet()) {
            result.put(key, new HashSet((Set)supermap.get(key)));
        }

        for(Object i : result.keySet()) {
            for(Object j : result.keySet()) {

                if(i.equals(j)) {
                    continue;
                }

                Set dst = result.get(i);
                Set src = result.get(j);

                if(!Collections.disjoint(dst, src)) {
                    dst.addAll(src);
                }

            }
        }

        return removeDuplicates(result);

    }

    private Map removeDuplicates(Map input) {

        Map reversed = new LinkedHashMap<>();
        for(Object key : input.keySet()) {
            reversed.putIfAbsent( input.get(key), key );
        }

        Map output = new LinkedHashMap<>();
        for(Object key : reversed.keySet()) {
            output.put( reversed.get(key), key );
        }

        return output;

    }

    public static void main(String[] args) {

        /*
        user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru
        user2 -> foo@gmail.com, ups@pisem.net
        user3 -> xyz@pisem.net, vasya@pupkin.com
        user4 -> ups@pisem.net, aaa@bbb.ru
        user5 -> xyz@pisem.net
        */

        SetMerger setMerger = new SetMergerImpl();

        HashSet input1 = new LinkedHashSet<>();

        input1.add( new HashSet<>(Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru")) );
        input1.add( new HashSet<>(Arrays.asList("foo@gmail.com", "ups@pisem.net")) );
        input1.add( new HashSet<>(Arrays.asList("xyz@pisem.net", "vasya@pupkin.com")) );
        input1.add( new HashSet<>(Arrays.asList("ups@pisem.net", "aaa@bbb.ru")) );
        input1.add( new HashSet<>(Arrays.asList("xyz@pisem.net")) );

        Set result1 = setMerger.merge(input1);

        Map input2 = new LinkedHashMap();
        int i = 1;
        for(Object emails : input1) {
            input2.put("user" + i, emails);
            i++;
        }

        Map result2 = setMerger.merge(input2);

        System.err.println("finish");

    }

}
