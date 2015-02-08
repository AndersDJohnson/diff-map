import groovy.util.logging.Slf4j
import org.junit.Test
import static org.junit.Assert.*


@Slf4j
class DiffMapTest {

        @Test
        void testMap() {
                List<String> a = [
                        'a', // 0
                        'b', // 1
                        'c', // 2
                        'd', // 3
                        'd2', // 4
                        'e' // 5
                ]

                List<String> b = [
                        'b', // 0
                        '0', // 1
                        'c', // 2
                        'd...', // 3
                        'e', // 4
                        'f' // 5
                ]

                Map map = DiffMap.map(a, b)

                println map

                Map<Integer, Integer> expected = [
                        0: null,
                        1: 0,
                        2: 2,
                        3: null,
                        4: null,
                        5: 4
                ]

                assertEquals(expected, map)
        }

        @Test
        void testMap2() {
                List<String> a = [
                        'a',
                        'b',
                        'c',
                        'd',
                        'e'
                ]

                List<String> b = [
                        'a',
                        'a2',
                        'a3',
                        'a4',
                        'd',
                        'd2',
                        'e',
                        'f',
                        'f2'
                ]

                Map map = DiffMap.map(a, b)

                println map

                Map<Integer, Integer> expected = [
                        0: 0,
                        1: null,
                        2: null,
                        3: 4,
                        4: 6
                ]

                assertEquals(expected, map)
        }

        @Test
        void testMap3() {
                List<String> a = [
                        'a',
                        'b',
                        'c',
                        'd',
                        'e',
                        'e2',
                        'f'
                ]

                List<String> b = [
                        'a2',
                        'a3',
                        'b',
                        'a4',
                        'd',
                        'd2',
                        'e',
                        'f',
                        'f2'
                ]

                Map map = DiffMap.map(a, b)

                println map

                Map<Integer, Integer> expected = [
                        0: null,
                        1: 2,
                        2: null,
                        3: 4,
                        4: 6,
                        5: null,
                        6: 7
                ]

                assertEquals(expected, map)
        }
}
