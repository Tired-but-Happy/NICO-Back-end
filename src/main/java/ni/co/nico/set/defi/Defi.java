package ni.co.nico.set;

import java.util.HashSet;
import java.util.Set;

public class Defi {
    private Set<String> contractNames;

    public Defi() {
        this.contractNames = new HashSet<>();
    }

    public void addContractName(String contractName) {
        contractNames.add(contractName);
    }

    public Set<String> getContractNames() {
        return contractNames;
    }

    public void setContractNames(Set<String> contractNames) {
        this.contractNames = contractNames;
    }
}
