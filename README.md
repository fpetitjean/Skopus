# Skopus
Exact discovery of the top-k sequential patterns under Leverage

# Preamble

This code was used to generate the results of the paper entitled: 
*Skopus: Mining top-k sequential patterns under Leverage*.
When using this repository, please cite:
```
@article{Petitjean2016-Skopus,
  author    = {Petitjean, Francois and Li, Tao and Tatti, Nikolaj and Webb, Geoffrey I.},
  title     = {Skopus: Mining top-k sequential patterns under Leverage},
  journal   = {Data Mining and Knowledge Discovery},
  volume    = {1},
  number    = {1},
  pages     = {1--25},
  year      = {2016}
}
```
Note the paper is available [here](Skopus-DMKD.pdf)


# Copyright
The code in `src` is provided under GLP License version 3. 

Datasets are provided for reproducibility and without any license. 

# Running
Simply call
```
java -Xmx1g InterestingnessMeasureMain <dataset> <output> -i2 -m1 -k100
```

