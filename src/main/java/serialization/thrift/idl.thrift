namespace java serialization.thrift

struct PersonID {
1: string cookie;
2: i64 user_id;
}

union PageID {
1: string url;
}

struct PageViewEdge {
1: required PersonID person;
2: required PageID page;
3: required i64 nonce;
}
