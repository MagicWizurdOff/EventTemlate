package dev.eventtemplate.teams;

import dev.eventtemplate.*;
import net.luckperms.api.*;
import net.luckperms.api.model.group.*;
import net.luckperms.api.model.user.*;
import net.luckperms.api.node.*;
import net.luckperms.api.node.types.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;

import java.util.*;
import java.util.concurrent.*;

public class LuckPermsApi {
    private LuckPerms lp;
    private Map<Groups, Group> teams;
    private Set<InheritanceNode> teamNodes;

    public LuckPermsApi() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider == null) {
            return;
        }
        lp = provider.getProvider();
        teams = new HashMap<>();
        teamNodes = new HashSet<>();
        createGroups();
        for (Groups g : Groups.values()) {
            teams.put(g, lp.getGroupManager().getGroup(g.getName()));
            teamNodes.add(InheritanceNode.builder(g.getPermissionName()).build());
        }

    }
    private void createGroups() {
        for (Groups g : Groups.values()) {
            GroupManager manager = lp.getGroupManager();
            String group_name = g.getName();
            int weight = g.getWeight();
            if (manager.getGroup(group_name) != null)
                return;
            Group group = lp.getGroupManager().createAndLoadGroup(group_name).join();
            group.data().add(PrefixNode.builder().prefix(g.getPrefix() + " &7").priority(weight).build());
            group.data().add(WeightNode.builder().weight(weight).build());
            manager.saveGroup(group);
            Main.log("&aCreated group &7" + g.getName());
        }
    }
    public List<Groups> getGroups(Player p) {
        List<Groups> groups = new ArrayList<>();
        for (Groups g : Groups.values()) {
            if (p.hasPermission(g.getPermissionName()))
                groups.add(g);
        }
        return groups;
    }
    public Groups getGroup(Player p) {
        for (Groups g : Groups.values()) {
            if (p.hasPermission(g.getPermissionName()))
                return g;
        }
        return null;
    }
    public boolean joinGroup(Player p, Groups g) {
        User user = lp.getPlayerAdapter(Player.class).getUser(p);
        Set<InheritanceNode> nodes = new HashSet<>();
        nodes.add(InheritanceNode.builder(teams.get(g)).expiry(3, TimeUnit.DAYS).value(true).build());
        nodes.add(InheritanceNode.builder("default").value(true).build());
        Set<InheritanceNode> groups = new HashSet<>(user.getNodes(NodeType.INHERITANCE));
        teamNodes.forEach(node -> {
            if (groups.contains(node))
                user.data().remove(node);
        });
        nodes.forEach(node -> user.data().add(node));
        return true;
    }
    public boolean leaveGroup(Player p) {
        User user = lp.getPlayerAdapter(Player.class).getUser(p);
        Set<InheritanceNode> groups = new HashSet<>(user.getNodes(NodeType.INHERITANCE));
        teamNodes.forEach(node -> {
            if (groups.contains(node))
                user.data().remove(node);
        });
        return true;
    }
}
