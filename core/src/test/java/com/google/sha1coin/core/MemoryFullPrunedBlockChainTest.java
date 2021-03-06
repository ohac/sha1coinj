package com.google.sha1coin.core;

import com.google.sha1coin.store.BlockStoreException;
import com.google.sha1coin.store.FullPrunedBlockStore;
import com.google.sha1coin.store.MemoryFullPrunedBlockStore;
import org.junit.Ignore;

/**
 * A MemoryStore implementation of the FullPrunedBlockStoreTest
 */
public class MemoryFullPrunedBlockChainTest extends AbstractFullPrunedBlockChainTest
{
    @Override
    public FullPrunedBlockStore createStore(NetworkParameters params, int blockCount) throws BlockStoreException
    {
        return new MemoryFullPrunedBlockStore(params, blockCount);
    }

    @Override
    public void resetStore(FullPrunedBlockStore store) throws BlockStoreException
    {
        //No-op for memory store, because it's not persistent
    }
}
