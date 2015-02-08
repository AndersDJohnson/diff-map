import difflib.ChangeDelta
import difflib.Chunk
import difflib.DeleteDelta
import difflib.Delta
import difflib.DiffUtils
import difflib.InsertDelta
import difflib.Patch

/**
 *
 */
public class DiffMapBack2 {

    public static Map<Integer, Integer> map(List<String> a, List<String> b) {
        Map<Integer, Integer> map = [:]

        Patch patch = DiffUtils.diff(a, b)
        List<Delta> deltas = patch.deltas

        println "deltas: $deltas"

        Map<Integer, Delta> deltaMap = [:]

        deltas.each { delta ->
            deltaMap[delta.original.position] = delta
        }

        println "deltaMap: $deltaMap"

        int j = 0

        for ( int i = 0; i < a.size(); ) {
            println '----------'
            println "map: $map"
            println "i: $i, j: $j"
            Delta delta = deltaMap[i]
            println "delta: ${delta?.class} $delta"
            if (delta?.class in DeleteDelta) {
                int size = delta.original.size()
                while (size-- > 0) {
                    map.put(i, null)
                    ++i
                }
            }
            else if (delta?.class in InsertDelta) {
                int size = delta.revised.size()
                while (size-- > 0) {
                    ++j
                }
                map.put(i, j)
                ++i
            }
            else if (delta?.class in ChangeDelta) {
                int osize = delta.original.size()
                while (osize-- > 0) {
                    map.put(i, null)
                    ++i
                }
                int rsize = delta.revised.size()
                while (rsize-- > 0) {
                    ++j
                }
            }
            else {
                map.put(i, j)
                ++i
                ++j
            }
            println "i: $i, j: $j"
            println "map: $map"
            println '----------'
        }

        return map
    }
}
