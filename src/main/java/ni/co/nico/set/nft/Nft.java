package ni.co.nico.set.nft;

import java.util.HashSet;
import java.util.Set;

public class Nft {
    private Set<String> contractNames;

    public Nft() {
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
