package Mushroom.Service.JoinServer;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class InviteTracker {
    private final Map<String, Invite> inviteMap;

    public InviteTracker() {
        inviteMap = new HashMap<>();
    }

    public void updateInvites(Guild guild) {
        guild.retrieveInvites().queue(invites -> {
            for (Invite invite : invites) {
                inviteMap.put(invite.getCode(), invite);
            }
        });
    }

    public CompletableFuture<Invite> findUsedInvite(Guild guild) {
        CompletableFuture<Invite> future = new CompletableFuture<>();

        guild.retrieveInvites().queue(invites -> {
            Invite invite_to_return = null;
            for (Invite invite : invites) {
                Invite oldInvite = inviteMap.get(invite.getCode());
                if (oldInvite != null && oldInvite.getUses() < invite.getUses()) {
                    invite_to_return = invite; // was found the invite used
                    break;
                }
            }
            future.complete(invite_to_return);
        });
        return future;
    }

    public Map<String, Invite> getInviteMap() {
        return inviteMap;
    }
}
