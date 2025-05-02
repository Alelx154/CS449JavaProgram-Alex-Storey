package mainFiles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReplayManager {
    private static final String REPLAY_DIR = "replays";
    
    public static void saveReplay(String replayName, int boardSize, List<String> moves, String gameType, String winnerInfo) {

        File dir = new File(REPLAY_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(REPLAY_DIR + "/" + replayName + ".txt"))) {
            writer.println("BoardSize:" + boardSize);
            writer.println("GameType:" + gameType);
            for (String move : moves) {
                writer.println(move);
            }
            writer.println("WinnerInfo:" + winnerInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List<String> loadReplay(String replayName) {
        List<String> moves = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(REPLAY_DIR + "/" + replayName + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                moves.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moves;
    }
    
    public static List<String> getAvailableReplays() {
        List<String> replays = new ArrayList<>();
        File dir = new File(REPLAY_DIR);
        if (dir.exists()) {
            File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
            if (files != null) {
                for (File file : files) {
                    replays.add(file.getName().replace(".txt", ""));
                }
            }
        }
        return replays;
    }
} 