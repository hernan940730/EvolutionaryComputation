package replace;

import individual.Individual;

public class SimpleReplacer implements ReplaceAlgorithm {

	@Override
	public Individual[] replace(Individual[] parents, Individual[] children) {
		return children;
	}

}
