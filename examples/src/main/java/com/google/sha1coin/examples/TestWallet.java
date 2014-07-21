import java.io.File;

import com.google.sha1coin.core.AbstractPeerEventListener;
import com.google.sha1coin.core.Block;
import com.google.sha1coin.core.ECKey;
import com.google.sha1coin.core.Message;
import com.google.sha1coin.core.NetworkParameters;
import com.google.sha1coin.core.Peer;
import com.google.sha1coin.kits.WalletAppKit;
import com.google.sha1coin.params.MainNetParams;
import com.google.sha1coin.params.TestNet3Params;
import com.google.sha1coin.utils.Threading;


public class TestWallet {

	private WalletAppKit appKit;
	private boolean testnet;
	private NetworkParameters params;

	public static void main(String[] args) throws Exception {
		boolean testnet = false;
		if (args.length == 1 && args[0].equals("-testnet")) {
			testnet = true;
		}
		new TestWallet(testnet).run();
	}

	public TestWallet(boolean testnet) {
		this.testnet = testnet;
		params = testnet ? TestNet3Params.get() : MainNetParams.get();
	}

	public void run() throws Exception {
		appKit = new WalletAppKit(params, new File("."), "sha1coins" + (testnet ? "-testnet" : "")) {
			@Override
			protected void onSetupCompleted() {
				if (wallet().getKeychainSize() < 1) {
					ECKey key = new ECKey();
					wallet().addKey(key);
				}
				
				peerGroup().setConnectTimeoutMillis(1000);
				
				System.out.println(appKit.wallet());
				
				peerGroup().addEventListener(new AbstractPeerEventListener() {
					@Override
					public void onPeerConnected(Peer peer, int peerCount) {
						super.onPeerConnected(peer, peerCount);
						System.out.println(String.format("onPeerConnected: %s %s",peer,peerCount));
					}
					@Override
					public void onPeerDisconnected(Peer peer, int peerCount) {
						super.onPeerDisconnected(peer, peerCount);
						System.out.println(String.format("onPeerDisconnected: %s %s",peer,peerCount));
					}
					@Override public void onBlocksDownloaded(Peer peer, Block block, int blocksLeft) {
						super.onBlocksDownloaded(peer, block, blocksLeft);
						System.out.println(String.format("%s blocks left (downloaded %s)",blocksLeft,block.getHashAsString()));
					}
					
					@Override public Message onPreMessageReceived(Peer peer, Message m) {
						System.out.println(String.format("%s -> %s",peer,m.getClass()));
						return super.onPreMessageReceived(peer, m);
					}
				},Threading.SAME_THREAD);
			}
		};
		
		appKit.startAndWait();
	}

}
