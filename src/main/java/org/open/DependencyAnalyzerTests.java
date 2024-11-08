package org.open;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class DependencyAnalyzerTests {
    /*
        repo1
            produces
                art1
                art2
            depends (repo2, repo3)
                art3
                art4
        repo2
            produces
                art3
                art4
            depends (repo3)
                art5
        repo3:
            produces
                art5
            depends (none)
        repo4
            produces
                art6
            depends (repo3)
                art5






    an unstable ordering: [repo3,repo4,repo2,repo1]
        repo4 could be in any order after repo3, and depending on the
        ordering of repos input, it may change

    a stabled, batched, ordering: [[repo3], [repo4, repo2], [repo1]]
        Create an ordering of collections that can be processed simultaneously.




     */
    @Test
    public void canCreateMinimalSequencedStepsToSatisfyDependencies() {

        var repos = new ArrayList<Repo>() {

            {
                add(new Repo("repo3",
                        new ArrayList<>() {{
                            add(new Artifact("art5"));
                        }},
                        new ArrayList<>()));
            }

            {
                add(new Repo("repo4",
                        new ArrayList<>() {{
                            add(new Artifact("art6"));
                        }},
                        new ArrayList<>() {{
                            add(new Artifact("art5"));
                        }}));
            }

            {
                add(new Repo("repo1",
                        new ArrayList<>() {
                            {
                                add(new Artifact("art1"));
                            }

                            {
                                add(new Artifact("art2"));
                            }
                        },
                        new ArrayList<>() {
                            {
                                add(new Artifact("art5"));
                            }

                            {
                                add(new Artifact("art6"));
                            }
                        }));
            }

            {
                add(new Repo("repo2",
                        new ArrayList<>() {
                            {
                                add(new Artifact("art3"));
                            }

                            {
                                add(new Artifact("art4"));
                            }
                        },
                        new ArrayList<>() {{
                            add(new Artifact("art5"));
                        }}));
            }

        };
        var da = new DependencyAnalyzer(repos);

        da.analyzeAllRepos();
        da.printOrdering();

        var r = da.getBatchedOrderingByRepoName();

        System.out.printf("%s%n", r);

        var expected = new ArrayList<Set<String>>() {
            {
                add(new HashSet<>() {{
                    add("repo3");
                }});
            }

            {
                add(new HashSet<>() {
                    {
                        add("repo4");
                    }

                    {
                        add("repo2");
                    }
                });
            }

            {
                add(new HashSet<>() {{
                    add("repo1");
                }});
            }
        };

        Assertions.assertEquals(expected, r);
    }


    public static class DependencyAnalyzer {
        final Map<String, Repo> reposByName;
        final Map<String, Repo> reposByArtifact = new HashMap<>();

        //working objects
        Stack<Repo> currentRepo = new Stack<>();
        Map<Repo, Set<Repo>> repoDependencyMap = new HashMap<>();
        Map<Repo, Set<Repo>> repoDependentsMap = new HashMap<>();

        //unstable sequential ordering
        List<Repo> orderedRepos = new ArrayList<>();

        public DependencyAnalyzer(Collection<Repo> repos) {
            if (repos == null)
                throw new IllegalArgumentException("repos is null");


            reposByName = repos.stream()
                    .filter(r -> {
                        if (!r.isValid())
                            throw new IllegalArgumentException("repo is not valid: " + r);
                        return true;
                    }).collect(Collectors.toMap(Repo::name, r -> r));


            repos.forEach(r -> r.artifacts().forEach(a -> {
                if (!a.isValid())
                    throw new IllegalArgumentException("artifact is not valid: " + a + " for repo " + r);
                reposByArtifact.put(a.name(), r);
            }));

            System.out.printf("%d repos%n", repos.size());
        }

        boolean updateDependencyMap(Repo from, Repo to) {
            Set<Repo> repoDeps;
            if (repoDependencyMap.containsKey(from)) {
                repoDeps = repoDependencyMap.get(from);
            } else
                repoDeps = new HashSet<>();

            if (repoDeps.contains(to))
                return false;

            repoDeps.add(to);
            repoDependencyMap.put(from, repoDeps);
            return true;
        }

        void updateDependentMap(Repo from, Repo to) {
            Set<Repo> repoDependents;
            if (repoDependentsMap.containsKey(to)) {
                repoDependents = repoDependentsMap.get(to);
            } else
                repoDependents = new HashSet<>();
            repoDependents.add(from);
            repoDependentsMap.put(to, repoDependents);
        }

        //return true if this is a new edge, otherwise false
        boolean addDependency(Repo from, Repo to) {

            if (!updateDependencyMap(from, to))
                return false;

            updateDependentMap(from, to);
            return true;
        }

        protected void analyze(Artifact artifact) {
            var repo = reposByArtifact.get(artifact.name());

            if (repo != null) {
                analyze(repo);
            }
        }

        void analyze(Repo repo) {

            //check for cycles
            if (currentRepo.contains(repo)) {
                printCycle(repo);
                throw new UnsupportedOperationException("Graph has cycle");
            }

            //add edge from parent to this
            if (!currentRepo.isEmpty()) {
                addDependency(currentRepo.peek(), repo);
            }

            //add self, and see if we have already visited
            if (!addDependency(repo, repo)) {
                return;
            }

            currentRepo.push(repo);
            for (var artifact : repo.dependencies()) {
                analyze(artifact);
            }
            currentRepo.pop();

            //update unstable dependency ordering
            orderedRepos.add(repo);
        }


        public void analyzeAllRepos() {
            for (var repo : reposByName.values()) {
                analyze(repo);
            }
        }


        public List<Set<Repo>> getBatchedOrdering() {

            List<Set<Repo>> orderedBatches = new ArrayList<>();
            Map<Repo, Set<Repo>> localMap = new HashMap<>(repoDependencyMap);
            for (int i = 0; i < localMap.size(); i++) {
                Set<Repo> items = getRepoWithNoDependency(localMap);
                if (!items.isEmpty()) {
                    orderedBatches.add(items);
                    removeItemsForDependency(items, localMap);
                }
            }
            return orderedBatches;
        }

        private void removeItemsForDependency(Set<Repo> repos, Map<Repo, Set<Repo>> allRepos) {
            if (repos != null)
            {
                Iterator<Map.Entry<Repo, Set<Repo>>> iterator = allRepos.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Repo, Set<Repo>> entry = iterator.next();
                    for(Repo repo:repos)
                    {
                        entry.getValue().remove(repo);
                    }
                }
            }
        }

        private Set<Repo> getRepoWithNoDependency(Map<Repo, Set<Repo>> repos) {
            Set<Repo> repoList = new HashSet<>();
            for (Repo repo : repos.keySet()) {
                if (repos.get(repo) != null &&
                        repos.get(repo).size() == 1 &&
                        repos.get(repo).contains(repo)) {
                    repoList.add(repo);
                }
            }
            return repoList;
        }




        private void removeRepo(Map<Repo, Set<Repo>> localMap, Repo repo) {
            Iterator<Map.Entry<Repo, Set<Repo>>> iterator = localMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Repo, Set<Repo>> entry = iterator.next();

                entry.getValue().remove(repo);
            }
        }

        List<Set<String>> getBatchedOrderingByRepoName() {
            return getBatchedOrderingByRepoName(getBatchedOrdering());
        }

        List<Set<String>> getBatchedOrderingByRepoName(List<Set<Repo>> repoBatchOrder) {
            var repoBatchList = new ArrayList<Set<String>>();
            repoBatchOrder.forEach(batch -> {
                var batchNames = new HashSet<String>();
                for (var bi : batch) {
                    batchNames.add(bi.name());
                }
                repoBatchList.add(batchNames);
            });
            return repoBatchList;
        }

        void printBatch(Collection<Repo> batch) {
            var sb = new StringBuffer();
            for (var r : batch) {
                if (!sb.isEmpty())
                    sb.append(",");
                sb.append(r.name());
            }
            System.out.printf("[%s]%n", sb);
        }

        void printBatchedOrdering(List<Set<Repo>> batchedOrdering) {
            batchedOrdering.forEach(this::printBatch);
        }

        public void printOrdering() {
            printBatch(orderedRepos);
        }

        protected void printCycle(Repo from) {
            var cycle = new ArrayList<Repo>();
            cycle.add(from);
            while (!currentRepo.isEmpty()) {
                var r = currentRepo.pop();
                cycle.add(r);
                if (r == from)
                    break;
            }
            printBatch(cycle);
        }
    }

    public static class Artifact {
        String name;

        public Artifact(String name) {
            this.name = name;
        }

        public String name() {
            return name;
        }

        public boolean isValid() {
            return name != null;
        }

        @Override
        public String toString() {
            return "Artifact{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public static class Repo {
        String name;
        Collection<Artifact> artifacts;
        Collection<Artifact> dependencies;

        public Repo(String name, Collection<Artifact> artifacts, Collection<Artifact> deps) {
            this.name = name;
            this.artifacts = artifacts;
            this.dependencies = deps;
        }

        public String name() {
            return name;
        }

        public Collection<Artifact> artifacts() {
            return artifacts;
        }

        public Collection<Artifact> dependencies() {
            return dependencies;
        }

        public boolean isValid() {
            return name != null && artifacts != null && dependencies != null;
        }

        public boolean areReferencesValid() {
            return dependencies.stream().allMatch(Artifact::isValid) &&
                    artifacts.stream().allMatch(Artifact::isValid);
        }

        @Override
        public String toString() {
            return "Repo{" +
                    "name='" + name + '\'' +
                    ", artifacts=" + artifacts +
                    ", dependencies=" + dependencies +
                    '}';
        }
    }
}

