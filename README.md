# D3 ScalaJS

## Examples

This library comes with a set of examples for how to use it. These are included
in the `d3-examples` sub-project. In order to view the example you first need to
compile them, by running:

```bash
sbt d3Examples/fastOptJS::webpack
```

After running this command, you can view the examples, by opening the
corresponding html page from `d3-examples/src/main/html`. Make sure to open the
pages using HTTP, so that the data files they try to access are indeed
accessible.

## Supported Modules

- [ ] d3-array
- [x] d3-axis
- [ ] d3-brush
- [ ] d3-chord
- [ ] d3-collection
- [x] d3-color
- [ ] d3-contour
- [ ] d3-dispatch
- [ ] d3-drag
- [x] d3-dsv
- [ ] d3-ease
- [x] d3-fetch
- [ ] d3-force
- [x] d3-format
- [ ] d3-geo
- [ ] d3-hexbin
- [ ] d3-hierarchy
- [x] d3-hsv
- [x] d3-interpolate
- [ ] d3-path
- [ ] d3-polygon
- [ ] d3-quadtree
- [ ] d3-queue
- [ ] d3-random
- [ ] d3-request
- [ ] d3-sankey
- [x] d3-scale-chromatic
- [x] d3-scale
- [ ] d3-selection-multi
- [ ] d3-selection
- [ ] d3-shape
- [x] d3-time-format
- [x] d3-time
- [x] d3-timer
- [ ] d3-tip
- [ ] d3-transition
- [ ] d3-voronoi
- [ ] d3-zoom

### Third Party

- [ ] d3-cloud-layout
- [ ] d3-box
- [ ] d3-slider
