package com.plusls.llsmanager.data;

import com.plusls.llsmanager.LlsManager;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LlsWhitelist extends AbstractConfig<LlsWhitelist.WhitelistData> {
    private WhitelistData whitelistData = new WhitelistData();

    public LlsWhitelist(Path dataFolderPath) {
        super(dataFolderPath.resolve("lls-whitelist.json"), WhitelistData.class);
    }

    public static class WhitelistData {
        public ConcurrentLinkedQueue<String> whiteList = new ConcurrentLinkedQueue<>();
    }

    public boolean query(String username) {
        return whitelistData.whiteList.contains(username);
    }

    public boolean add(String username) {
        if (whitelistData.whiteList.add(username)) {
            return save();
        }
        LlsManager.logger().error("whiteList.add error.");
        return false;
    }

    public boolean remove(String username) {
        if (whitelistData.whiteList.remove(username)) {
            return save();
        }
        LlsManager.logger().error("whiteList.remove error.");
        return false;
    }

    public ArrayList<String> search(String username) {
        ArrayList<String> ret = new ArrayList<>();
        for (String whiteListUsername : whitelistData.whiteList) {
            if (whiteListUsername.contains(username)) {
                ret.add(whiteListUsername);
            }
        }
        return ret;
    }

    @Override
    protected WhitelistData getData() {
        return whitelistData;
    }

    @Override
    protected void setData(WhitelistData data) {
        whitelistData = data;
    }
}
