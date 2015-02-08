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
public class DiffMapBack {

    public static Map<Integer, Integer> map(List<String> a, List<String> b) {
        Map<Integer, Integer> map = [:]

        Patch patch = DiffUtils.diff(a, b)
        List<Delta> deltas = patch.deltas

        println deltas

        Map<Integer, Delta> deltaMap = [:]

        deltas.each { delta ->
            deltaMap[delta.original.position] = delta
        }

        int ai = 0
        int bi = 0

        a.eachWithIndex { line, i  ->
            Delta delta = deltaMap[ai]
            println '----------'
            println delta
            println ai + ' ' + bi
            if (ai != i) {
                ++ai
                return
            }
            if (delta) {
                Chunk original = delta.original
                Chunk revised = delta.revised
                Class clazz = delta.class
                if (clazz in DeleteDelta.class) {
                    int size = original.lines.size()
                    println "delete $size"
//                    while (size-- > 0) {
                        map[ai] = null
                        ++ai
//                    }
                }
                else if (clazz in InsertDelta.class) {
                    int size = revised.lines.size()
                    println "insert $size"
//                    while (size-- > 0) {
                        ++bi
//                    }
                    map[ai] = bi
                }
                else if (clazz in ChangeDelta.class) {
                    int size = original.lines.size() - revised.lines.size()
                    println "change $size"
                    if (size > 0) {
//                        while (size-- > 0) {
                            map[ai] = null
                            ++ai
                            ++bi
//                        }
                    }
                    else if (size < 0) {
//                        while (size-- < 0) {
                            map[ai] = null
                            ++ai
                            ++bi
//                        }
                    }
                    else {
                        map[ai] = bi
                        ++ai
                        ++bi
                    }
                }
                else {
                    throw new Exception("Unhandled delta class: " + delta.class)
                }
            }
            else {
                map[ai] = bi
                ++ai
                ++bi
            }
            println map
        }

        return map
    }
}
