### Search Engine

This is a search engine based on graph data structure. It first crawl and index all the website (node) in a given Xml file. Then it ranks every node based on its ratio between in_degree and our-degree through the formula:
$$
pr(v) = (1-d) + d *(pr(w1)/out(w1)+pr(w2)/out(w2)+ ...pr(wk)/out(wk))
$$
where $pr(w)$ be the page rank of a node w.

​           $out(w)$ be the out-degree of a node w.

​          $w1,w2,w3,...$ be the nodes in the graph that have an out page going into $v$.

The constant $d$ is called the damping factor.

A `getResults` method takes one query word and return an array of websites that is sorted ($O(nlogn)$) in descending order of their ranks.